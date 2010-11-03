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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.acl.DefaultAclFactory;
import org.onesocialweb.xml.dom.AclDomReader;
import org.onesocialweb.xml.dom.imp.DefaultAclDomReader;
import org.w3c.dom.Element;

public class AclDomReaderTest {

	private AclDomReader aclDomReader;
	
	@Before
	public void setUp() throws Exception {
		aclDomReader = new DefaultAclDomReader() {

			@Override
			protected AclFactory getAclFactory() {
				return new DefaultAclFactory();
			}
			
		};
	}
	
	@Test
	public void testLoadXml() throws DocumentException {
		AclRule rule = readRule("acl-rule.xml");
		assertNotNull(rule);
		
		List<AclAction> actions = rule.getActions();
		assertNotNull(actions);
		assertEquals(1, actions.size());
		
		AclAction action = actions.get(0);
		assertEquals(AclAction.PERMISSION_GRANT, action.getPermission());
		assertEquals(AclAction.ACTION_VIEW, action.getName());
		
		List<AclSubject> subjects = rule.getSubjects();
		assertNotNull(subjects);
		assertEquals(1, subjects.size());
		
		AclSubject subject = subjects.get(0);
		assertEquals(AclSubject.EVERYONE, subject.getType());
	}

	protected AclRule readRule(String path) throws DocumentException {
		org.w3c.dom.Document document = readDocument(path);
        Element root = (Element) document.getFirstChild();
        
        assertEquals(root.getNodeName(), "acl-rule");
        assertEquals(root.getNamespaceURI(), "http://onesocialweb.org/spec/1.0/");
        
        return aclDomReader.readRule(root);
	}
	
	protected org.w3c.dom.Document readDocument(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream(path));
        DOMWriter writer = new DOMWriter();
        return writer.write(document);		
	}

}
