package org.onesocialweb.xml.dom;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.onesocialweb.xml.namespace.OStatus;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Element;

public class XrdWriter {

	public void writeAccount(String jid, String accountsPath, String domainName) throws IOException{
		String profilePage="http://"+domainName+"/profiles/"+getUsername(jid)+".html";
		String updatesUrl="http://"+domainName+"/updates/"+getUsername(jid)+".atom";
		
		DOMDocument domDocument = new DOMDocument();		
		Element xrd = (Element) domDocument.appendChild(domDocument.createElementNS(OStatus.XRD_NAMESPACE, OStatus.XRD_ELEMENT));
		Element subject = (Element) xrd.appendChild(xrd.getOwnerDocument().createElementNS(OStatus.XRD_NAMESPACE,OStatus.SUBJECT_ELEMENT));
		subject.appendChild(subject.getOwnerDocument().createTextNode("acct:"+jid));
		Element alias =(Element) xrd.appendChild(xrd.getOwnerDocument().createElementNS(OStatus.XRD_NAMESPACE, OStatus.ALIAS_ELEMENT));
		alias.appendChild(alias.getOwnerDocument().createTextNode(profilePage));	
		addLink(xrd, OStatus.WEBFINGER_PROFILE_NAMESPACE, profilePage, "text/html");
		addLink(xrd, OStatus.HCARD_NAMESPACE, profilePage, "text/html" );
		addLink(xrd, "describedby", profilePage, "text/html");
		addLink(xrd, Onesocialweb.SERVICE_NAMESPACE, "xmpp:"+jid, null);
		addLink(xrd, OStatus.ATOM_UPDATES, updatesUrl, "application/atom+xml");

		
		FileOutputStream fos = new FileOutputStream(accountsPath+"/"+jid+".xml");
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(fos, format);
		writer.write(domDocument);
		writer.flush();
 	
		
	}
	
	private void addLink(Element e, String rel, String href, String type){
		Element link =e.getOwnerDocument().createElementNS(OStatus.XRD_NAMESPACE,"Link");
		if (rel!=null) 
			link.setAttribute("rel", rel);
		if (href!=null) 
			link.setAttribute("href", href);
		if (type!=null) 
			link.setAttribute("type", type);
		e.appendChild(link);
		
	}
	
	private static String getUsername(String jid){
		int index = jid.indexOf("@");
		return jid.substring(0, index);
	}
	
}
