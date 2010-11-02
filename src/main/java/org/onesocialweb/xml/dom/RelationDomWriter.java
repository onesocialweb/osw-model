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

import static org.onesocialweb.xml.dom.DomHelper.appendTextNode;

import java.util.Date;

import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class RelationDomWriter {

	private final AclDomWriter aclDomWriter;
	
	public RelationDomWriter() {
		this.aclDomWriter = getAclDomWriter();
	}
	
	public Element toElement(Relation relation, Document document) {
		Element root = document.createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.RELATION_ELEMENT);
		document.appendChild(root);
		write(relation, root);
		return root;
	}

	public Element toElement(Relation relation, Element parent) {
		Element root = (Element) parent.appendChild(parent.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.RELATION_ELEMENT));
		write(relation, root);
		return root;
	}

	public void write(Relation relation, Element target) {

		if (relation.hasId()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.ID_ELEMENT, relation.getId());
		if (relation.hasFrom()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.FROM_ELEMENT, relation.getFrom());
		if (relation.hasTo()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.TO_ELEMENT, relation.getTo());
		if (relation.hasNature()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.NATURE_ELEMENT, relation.getNature());
		if (relation.hasStatus()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.STATUS_ELEMENT, relation.getStatus());
		if (relation.hasComment()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.COMMENT_ELEMENT, relation.getComment());
		if (relation.hasMessage()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.MESSAGE_ELEMENT, relation.getMessage());		
		if (relation.hasPublished()) appendTextNode(target, Onesocialweb.NAMESPACE, Onesocialweb.PUBLISHED_ELEMENT, format(relation.getPublished()));
		
		if (relation.hasAclRules()) {
			for (AclRule rule : relation.getAclRules()) {
				Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_RULE_ELEMENT));
				aclDomWriter.write(rule, element);
			}
		}
	}
	
	protected abstract AclDomWriter getAclDomWriter();

	protected abstract String format(Date date);
	
}
