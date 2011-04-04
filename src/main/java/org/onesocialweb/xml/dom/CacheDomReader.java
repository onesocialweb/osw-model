package org.onesocialweb.xml.dom;

import org.onesocialweb.model.cache.DefaultDomainCache;
import org.onesocialweb.model.cache.DomainCache;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CacheDomReader {

	public DomainCache readCache(Element e){
		DomainCache dc= new DefaultDomainCache();

		NodeList children=e.getChildNodes();
		
		for (int i=0; i<children.getLength(); i++) {
			
			Node node= children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				if (node.getNodeName().equalsIgnoreCase("name"))
					dc.setDomain(node.getTextContent());
				else if (node.getNodeName().equalsIgnoreCase("protocols"))
					dc.setProtocols(node.getTextContent());
			}
				
		}
		
		return dc;
	}
}
