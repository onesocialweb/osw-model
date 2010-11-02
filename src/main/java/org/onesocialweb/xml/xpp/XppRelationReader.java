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

import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.model.relation.RelationFactory;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class XppRelationReader implements XppReader<Relation> {

	private final RelationFactory relationFactory;
	
	private final XppAclReader xppAclReader;

	public XppRelationReader() {
		this.relationFactory = getRelationFactory();
		this.xppAclReader = getXppAclReader();
	}

	@Override
	public Relation parse(XmlPullParser parser) throws XmlPullParserException, IOException {

		// Verify that we are on the right token
		if (!(parser.getNamespace().equals(Onesocialweb.NAMESPACE) 
				&& parser.getName().equals(Onesocialweb.RELATION_ELEMENT))) {
			throw new XmlPullParserException("Unexpected token " + parser);
		}

		//Proceed with parsing
		final Relation relation = relationFactory.relation();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ID_ELEMENT)) {
						relation.setId(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.FROM_ELEMENT)) {
						relation.setFrom(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.TO_ELEMENT)) {
						relation.setTo(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.MESSAGE_ELEMENT)) {
						relation.setMessage(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.COMMENT_ELEMENT)) {
						relation.setComment(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.NATURE_ELEMENT)) {
						relation.setNature(parser.nextText().trim());
					} else if (name.equals(Onesocialweb.STATUS_ELEMENT)) {
						relation.setStatus(parser.nextText().trim());
					}
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						relation.addAclRule(xppAclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(Onesocialweb.NAMESPACE)
						&& name.equals(Onesocialweb.RELATION_ELEMENT)) {
					done = true;
				}
			}
		}
		return relation;
	}
		
	abstract protected AclFactory getAclFactory();
	
	abstract protected RelationFactory getRelationFactory();

	abstract protected XppAclReader getXppAclReader();
}

