package org.onesocialweb.xml.dom;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.onesocialweb.xml.namespace.Microformats;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Element;

public class XrdWriter {

	public void writeAccount(String jid, String accountsPath, String pagePath) throws IOException{
		String profilePage="http://"+pagePath+"/"+getUsername(jid)+".html";
		
		DOMDocument domDocument = new DOMDocument();		
		Element xrd = (Element) domDocument.appendChild(domDocument.createElementNS(Microformats.XRD_NAMESPACE, Microformats.XRD_ELEMENT));
		Element subject = (Element) xrd.appendChild(xrd.getOwnerDocument().createElementNS(Microformats.XRD_NAMESPACE,Microformats.SUBJECT_ELEMENT));
		subject.appendChild(subject.getOwnerDocument().createTextNode("acct:"+jid));
		Element alias =(Element) xrd.appendChild(xrd.getOwnerDocument().createElementNS(Microformats.XRD_NAMESPACE, Microformats.ALIAS_ELEMENT));
		alias.appendChild(alias.getOwnerDocument().createTextNode(profilePage));	
		addLink(xrd, Microformats.WEBFINGER_PROFILE_NAMESPACE, profilePage, "text/html");
		addLink(xrd, Microformats.HCARD_NAMESPACE, profilePage, "text/html" );
		addLink(xrd, "describedby", profilePage, "text/html");
		addLink(xrd, Onesocialweb.SERVICE_NAMESPACE, "xmpp:"+jid, null);

		
		FileOutputStream fos = new FileOutputStream(accountsPath+"/"+jid+".xml");
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(fos, format);
		writer.write(domDocument);
		writer.flush();
 	
		
	}
	
	private void addLink(Element e, String rel, String href, String type){
		Element link =e.getOwnerDocument().createElementNS(Microformats.XRD_NAMESPACE,"Link");
		if (rel!=null) 
			link.setAttribute("rel", rel);
		if (href!=null) 
			link.setAttribute("href", href);
		if (type!=null) 
			link.setAttribute("type", type);
		e.appendChild(link);
		
	}
	
	public static String getUsername(String jid){
		int index = jid.indexOf("@");
		return jid.substring(0, index);
	}
	
}
