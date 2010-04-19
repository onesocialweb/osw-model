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

import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AclDomReader {
	
	private final AclFactory factory;
	
	public AclDomReader() {
		this.factory = getAclFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.AclDomReader#readRule(org.w3c.dom.Element)
	 */
	public AclRule readRule(Element element) {
		AclRule rule = factory.aclRule();

		// Process the actions
		NodeList actions = element.getElementsByTagNameNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_ACTION_ELEMENT);
		for (int i = 0; i <  actions.getLength(); i++) {
			Node action = actions.item(i);
			if (action.getNodeType() == Node.ELEMENT_NODE) {
				rule.addAction(readAction((Element) action));
			}
		}

		// Process the subjects
		NodeList subjects = element.getElementsByTagNameNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_SUBJECT_ELEMENT);
		for (int i = 0; i <  subjects.getLength(); i++) {
			Node subject = subjects.item(i);
			if (subject.getNodeType() == Node.ELEMENT_NODE) {
				rule.addSubject(readSubject((Element) subject));
			}
		}

		return rule;
	}
	
	protected AclAction readAction(Element element) {
		AclAction action = factory.aclAction();
		action.setName(element.getTextContent().trim());
		action.setPermission(element.getAttribute(Onesocialweb.PERMISSION_ATTRIBUTE));
		return action;
	}

	protected AclSubject readSubject(Element element) {
		AclSubject subject = factory.aclSubject();
		subject.setType(element.getAttribute(Onesocialweb.TYPE_ATTRIBUTE));
		subject.setName(element.getTextContent().trim());
		return subject;
	}

	protected abstract AclFactory getAclFactory();
	
}
