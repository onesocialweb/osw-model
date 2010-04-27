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
package org.onesocialweb.xml.writer;

import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.xml.namespace.Onesocialweb;

public class RelationXmlWriter extends XmlWriter {

	public String toXml(Relation relation) {
		toXml(relation, new StringBuffer());
		return buffer.toString();
	}
	
	public void toXml(Relation relation, StringBuffer buffer) {
		// Prepare for writing
		this.buffer = buffer;

		// Open the entry tag
		startTag(Onesocialweb.RELATION_ELEMENT);
		attribute("xmlns", Onesocialweb.NAMESPACE);
		endOpen();

		// Dump the simple stuff
		if (relation.hasId()) text(Onesocialweb.ID_ELEMENT, relation.getId());
		if (relation.hasFrom()) text(Onesocialweb.FROM_ELEMENT, relation.getFrom());
		if (relation.hasTo()) text(Onesocialweb.TO_ELEMENT, relation.getTo());
		if (relation.hasStatus()) text(Onesocialweb.STATUS_ELEMENT, relation.getStatus());
		if (relation.hasNature()) text(Onesocialweb.NATURE_ELEMENT, relation.getNature());
		if (relation.hasComment()) text(Onesocialweb.COMMENT_ELEMENT, relation.getComment());
		if (relation.hasMessage()) text(Onesocialweb.MESSAGE_ELEMENT, relation.getMessage());

		// Dump access control rules linked to this entry
		for (AclRule rule : relation.getAclRules()) {
			openTag(Onesocialweb.ACL_RULE_ELEMENT);
			if (rule.hasActions()) {
				for (AclAction action : rule.getActions()) {
					startTag(Onesocialweb.ACL_ACTION_ELEMENT);
					attribute(Onesocialweb.PERMISSION_ATTRIBUTE, action.getPermission());
					endOpen();
					if (action.hasName())
						buffer.append(xmlEncode(action.getName()));
					closeTag(Onesocialweb.ACL_ACTION_ELEMENT);
				}
			}
			if (rule.hasSubjects()) {
				for (AclSubject subject : rule.getSubjects()) {
					startTag(Onesocialweb.ACL_SUBJECT_ELEMENT);
					attribute(Onesocialweb.TYPE_ATTRIBUTE, subject.getType());
					endOpen();
					if (subject.hasName())
						buffer.append(xmlEncode(subject.getName()));
					closeTag(Onesocialweb.ACL_SUBJECT_ELEMENT);
				}
			}
			closeTag(Onesocialweb.ACL_RULE_ELEMENT);
		}
		
		// Close
		closeTag(Onesocialweb.RELATION_ELEMENT);
	}
}
