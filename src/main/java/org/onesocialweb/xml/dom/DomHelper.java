/*
 *  Copyright 2010 Vodafone Group Services Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *    
 */
package org.onesocialweb.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomHelper {

	public static String getElementText(Element element, String tagName, String namespace) {
		Element e = getElement(element, tagName, namespace);
		if (e != null) {
			String text = ((Element) e).getTextContent().trim();
			if (text != null) {
				text = text.trim();
				if (text.length() > 0) {
					return text;
				}
			}
		} 
		return null;
	}
	
	public static Element getElement(Element element, String tagName, String namespace) {
		NodeList nodes = element.getElementsByTagNameNS(namespace, tagName);
		if (nodes.getLength() > 0) {
			return ((Element) nodes.item(0));
		} else {
			return null;
		}		
	}
	
	public static String getElementAttribute(Element element, String name) {
		String text = element.getAttribute(name);
		if (text != null) {
			text = text.trim();
			if (text.length() > 0) {
				return text;
			}
		}
		return null;
	}
	
	public static Element appendTextNode(Element element, String namespace, String name, String content) {
		final Document document = element.getOwnerDocument();
		final Element node = document.createElementNS(namespace, name);
		element.appendChild(node);
		setTextContent(node, content);
		return element;
	}
	
	public static void setTextContent(Element element, String content) {
		if (content != null && content.length() > 0) {
			element.appendChild(element.getOwnerDocument().createTextNode(content));
		}		
	}
	
	public static Element appendParametersNode(Element element, String namespace, String name, String content) {
		final Document document = element.getOwnerDocument();		
		final Element parametersNode = document.createElementNS(namespace, "parameters");
		element.appendChild(parametersNode);
		
		final Element childNode = document.createElementNS(namespace, name);	
		parametersNode.appendChild(childNode);
		
		setTextContent(childNode, content);				
		return element;
	}
}
