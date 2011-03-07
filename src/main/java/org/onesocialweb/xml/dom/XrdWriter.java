package org.onesocialweb.xml.dom;

import org.dom4j.dom.DOMDocument;
import org.onesocialweb.xml.namespace.Microformats;
import org.w3c.dom.Element;

public class XrdWriter {

	public void writeAccount(String jid, String baseUrl){
		String profilePage=baseUrl+"/profiles/"+getUsername(jid);
		
		DOMDocument domDocument = new DOMDocument();		
		Element xrd = (Element) domDocument.appendChild(domDocument.createElementNS(Microformats.XRD_NAMESPACE, Microformats.XRD_ELEMENT));
		Element subject = (Element) xrd.appendChild(xrd.getOwnerDocument().createElement(Microformats.SUBJECT_ELEMENT));
		subject.setTextContent(jid);
		Element alias =(Element) xrd.appendChild(xrd.getOwnerDocument().createElement(Microformats.ALIAS_ELEMENT));
		alias.setTextContent(profilePage);	
		addLink(xrd, Microformats.WEBFINGER_PROFILE_NAMESPACE, profilePage, "text/html");
		addLink(xrd, Microformats.HCARD_NAMESPACE, profilePage, "text/html" );
		addLink(xrd, "describedby", profilePage, "text/html");
		
	}
	
	private void addLink(Element e, String rel, String href, String type){
		e.appendChild(e.getOwnerDocument().createElement("Link"));
		e.setAttribute("rel", rel);
		e.setAttribute("href", href);
		e.setAttribute("type", type);
	}
	
	public static String getUsername(String jid){
		int index = jid.indexOf("@");
		return jid.substring(index+1);		
	}
	
}
