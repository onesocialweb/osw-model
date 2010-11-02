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

import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.model.relation.RelationFactory;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class RelationDomReader {

	private final RelationFactory factory;
	
	private final AclDomReader aclDomReader;

	public RelationDomReader() {
		this.factory = getRelationFactory();
		this.aclDomReader = getAclDomReader();
	}
	
	public Relation readElement(Element element) {
		Relation relation = factory.relation();
		
		// Fetch the basic stuff
		relation.setId(DomHelper.getElementText(element, Onesocialweb.ID_ELEMENT, Onesocialweb.NAMESPACE));
		relation.setFrom(DomHelper.getElementText(element, Onesocialweb.FROM_ELEMENT, Onesocialweb.NAMESPACE));
		relation.setTo(DomHelper.getElementText(element, Onesocialweb.TO_ELEMENT, Onesocialweb.NAMESPACE));
		relation.setNature(DomHelper.getElementText(element, Onesocialweb.NATURE_ELEMENT, Onesocialweb.NAMESPACE));
		relation.setStatus(DomHelper.getElementText(element, Onesocialweb.STATUS_ELEMENT, Onesocialweb.NAMESPACE));		
		relation.setComment(DomHelper.getElementText(element, Onesocialweb.COMMENT_ELEMENT, Onesocialweb.NAMESPACE));
		relation.setMessage(DomHelper.getElementText(element, Onesocialweb.MESSAGE_ELEMENT, Onesocialweb.NAMESPACE));			
		
		// Get the acl rules
		NodeList rules = element.getElementsByTagNameNS(Onesocialweb.NAMESPACE, "acl-rule");
		for(int i=0; i < rules.getLength(); i++) {
			relation.addAclRule(aclDomReader.readRule((Element) rules.item(i)));
		}
		
		return relation;
	}
	
	protected abstract RelationFactory getRelationFactory();
	
	protected abstract AclDomReader getAclDomReader();

}
