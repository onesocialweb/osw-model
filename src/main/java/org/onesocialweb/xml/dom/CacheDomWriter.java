package org.onesocialweb.xml.dom;

import org.onesocialweb.model.cache.DomainCache;
import org.w3c.dom.Element;

public class CacheDomWriter {

	
	public Element toElement(DomainCache entry, Element parent) {
		
		write(entry, parent);
		return parent;
	}
	
	public void write(DomainCache entry, Element domainElement){
		
		Element nameElement=domainElement.getOwnerDocument().createElementNS("http://onesocialweb.org/extensions/1.0/ostatus#cache", "name");
		Element protocolsElement=domainElement.getOwnerDocument().createElementNS("http://onesocialweb.org/extensions/1.0/ostatus#cache", "protocols");
		domainElement.appendChild(nameElement);
		domainElement.appendChild(protocolsElement);
		
		nameElement.appendChild(nameElement.getOwnerDocument().createTextNode(entry.getDomain()));
		protocolsElement.appendChild(protocolsElement.getOwnerDocument().createTextNode(entry.getProtocols()));
	}
}
