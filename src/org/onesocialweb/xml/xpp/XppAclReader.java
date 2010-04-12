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
package org.onesocialweb.xml.xpp;

import java.io.IOException;

import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class XppAclReader implements XppReader<AclRule> {

	private final AclFactory aclFactory;
	
	public XppAclReader() {
		this.aclFactory = getAclFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.acl.XppAclReader#parse(org.xmlpull.v1.XmlPullParser)
	 */
	@Override
	public AclRule parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		final AclRule rule = aclFactory.aclRule();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_ACTION_ELEMENT)) {
						rule.addAction(parseAclAction(parser));
					} else if (name.equals(Onesocialweb.ACL_SUBJECT_ELEMENT)) {
						rule.addSubject(parseAclSubject(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(Onesocialweb.NAMESPACE)
						&& name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
					done = true;
				}
			}
		}
		return rule;
	}
	
	protected AclSubject parseAclSubject(XmlPullParser parser) throws XmlPullParserException, IOException {
		final AclSubject subject = aclFactory.aclSubject();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(Onesocialweb.TYPE_ATTRIBUTE)) {
				subject.setType(value);
			} 
		}
		String name = parser.nextText().trim();
		if (name.length() > 0) {
			subject.setName(name);
		}
		return subject;
	}
	
	protected AclAction parseAclAction(XmlPullParser parser) throws XmlPullParserException, IOException {
		final AclAction action = aclFactory.aclAction();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(Onesocialweb.PERMISSION_ATTRIBUTE)) {
				action.setPermission(value);
			} 
		}
		String name = parser.nextText().trim();
		if (name.length() > 0) {
			action.setName(name);
		}
		return action;
	}

	abstract protected AclFactory getAclFactory();
	
}
