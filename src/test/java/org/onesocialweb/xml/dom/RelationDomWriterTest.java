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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;

import org.dom4j.DocumentException;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.acl.DefaultAclFactory;
import org.onesocialweb.model.relation.DefaultRelationFactory;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.model.relation.RelationFactory;
import org.onesocialweb.xml.dom.RelationDomWriter;
import org.onesocialweb.xml.dom.imp.DefaultRelationDomWriter;
import org.w3c.dom.Element;

public class RelationDomWriterTest {

	@Test
	public void entryToXML() throws DocumentException, IOException {
		RelationFactory relationFactory = new DefaultRelationFactory();
		
		Relation relation = relationFactory.relation();
		relation.setId("urn:uuid:0bfb71a4-d8fd-4410-b119-199c3596f296");
		relation.setFrom("romeo@montague.lit");
		relation.setTo("juliet@capulet.lit");
		relation.setNature(Relation.Nature.COLLEAGUE);
		relation.setStatus(Relation.Status.REQUEST);
		relation.setComment("A really nice girl :-)");
		relation.setMessage("Dude ! Behave !");
		relation.setPublished(Calendar.getInstance().getTime());
		
		AclFactory aclFactory = new DefaultAclFactory();
		AclRule rule = aclFactory.aclRule();
		rule.addAction(aclFactory.aclAction(AclAction.ACTION_VIEW, AclAction.PERMISSION_GRANT));
		rule.addSubject(aclFactory.aclSubject(null, AclSubject.EVERYONE));
		relation.addAclRule(rule);		

		DOMDocument document = new DOMDocument();
		RelationDomWriter writer = new DefaultRelationDomWriter();
		Element element = writer.toElement(relation, document);
		assertNotNull(element);
		
		DOMReader reader = new DOMReader();		
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter( System.out, format );
	}
}
