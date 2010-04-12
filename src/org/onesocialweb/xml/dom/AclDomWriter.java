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

import static org.onesocialweb.xml.dom.DomHelper.setTextContent;

import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AclDomWriter {

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomWriter#toElement(org.onesocialweb.model.acl.AclRule, org.w3c.dom.Document)
	 */
	public Element toElement(AclRule rule, Document document) {
		Element root = document.createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_RULE_ELEMENT);
		document.appendChild(root);
		write(rule, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomWriter#toElement(org.onesocialweb.model.acl.AclRule, org.w3c.dom.Element)
	 */
	public Element toElement(AclRule rule, Element parent) {
		Element root = (Element) parent.appendChild(parent.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_RULE_ELEMENT));
		write(rule, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomWriter#write(org.onesocialweb.model.acl.AclRule, org.w3c.dom.Element)
	 */
	public void write(AclRule rule, Element target) {
		for (AclAction action : rule.getActions()) {
			Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_ACTION_ELEMENT));
			write(action, element);
		}
		for (AclSubject subject : rule.getSubjects()) {
			Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_SUBJECT_ELEMENT));
			write(subject, element);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomWriter#write(org.onesocialweb.model.acl.AclAction, org.w3c.dom.Element)
	 */
	public void write(AclAction action, Element target) {
		if (action.getName() != null) setTextContent(target, action.getName());
		if (action.getPermission() != null) target.setAttribute(Onesocialweb.PERMISSION_ATTRIBUTE, action.getPermission());
	}
		
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomWriter#write(org.onesocialweb.model.acl.AclSubject, org.w3c.dom.Element)
	 */
	public void write(AclSubject subject, Element target) {
		if (subject.getName() != null) setTextContent(target, subject.getName());
		if (subject.getType() != null) target.setAttribute(Onesocialweb.TYPE_ATTRIBUTE, subject.getType());
	}
}
