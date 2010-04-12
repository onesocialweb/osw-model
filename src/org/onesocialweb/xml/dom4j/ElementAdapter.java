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
package org.onesocialweb.xml.dom4j;

import java.util.List;

import org.dom4j.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class ElementAdapter implements Element {

	private org.dom4j.Element element;
	
	public ElementAdapter(org.dom4j.Element element) {
		this.element = element;
	}
	
	@Override
	public String getAttribute(String name) {
		return element.attributeValue(name);
	}

	@Override
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		return element.attributeValue(QName.get(localName, namespaceURI));
	}

	@Override
	public Attr getAttributeNode(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		NodeListAdapter nodeList = new NodeListAdapter();
		List<?> nodes = element.elements(name);
		for (Object object : nodes) {
			Element e = new ElementAdapter((org.dom4j.Element) object);
			nodeList.addElement(e);
		}
		return nodeList;
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
		NodeListAdapter nodeList = new NodeListAdapter();
		List<?> nodes = element.elements(QName.get(localName, namespaceURI));
		for (Object object : nodes) {
			Element e = new ElementAdapter((org.dom4j.Element) object);
			nodeList.addElement(e);
		}
		return nodeList;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		return element.getName();
	}

	@Override
	public boolean hasAttribute(String name) {
		return element.attributeValue(name) != null;
	}

	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		return element.attributeValue(QName.get(localName, namespaceURI)) != null;
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public Node appendChild(Node newChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node cloneNode(boolean deep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NamedNodeMap getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getChildNodes() {
		NodeListAdapter nodeList = new NodeListAdapter();
		List<?> nodes = element.elements();
		for (Object object : nodes) {
			Element e = new ElementAdapter((org.dom4j.Element) object);
			nodeList.addElement(e);
		}
		return nodeList;
	}

	@Override
	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getFirstChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getLastChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		return element.getName();
	}

	@Override
	public String getNamespaceURI() {
		return element.getNamespaceURI();
	}

	@Override
	public Node getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNodeName() {
		return element.getName();
	}

	@Override
	public short getNodeType() {
		return element.getNodeType();
	}

	@Override
	public String getNodeValue() throws DOMException {
		return element.getStringValue();
	}

	@Override
	public Document getOwnerDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrefix() {
		return element.getNamespacePrefix();
	}

	@Override
	public Node getPreviousSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTextContent() throws DOMException {
		return element.getStringValue();
	}

	@Override
	public Object getUserData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAttributes() {
		return element.attributeCount() > 0;
	}

	@Override
	public boolean hasChildNodes() {
		return element.elements().size() > 0;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualNode(Node arg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSameNode(Node other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupported(String feature, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void normalize() {
		// TODO Auto-generated method stub

	}

	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPrefix(String prefix) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTextContent(String textContent) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

}
