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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.onesocialweb.model.relation.Relation;
import org.onesocialweb.xml.dom.RelationDomReader;
import org.onesocialweb.xml.dom.imp.DefaultRelationDomReader;
import org.onesocialweb.xml.dom4j.ElementAdapter;
import org.onesocialweb.xml.namespace.Onesocialweb;

public class RelationDomReaderTest {

	private RelationDomReader relationDomReader;

	@Before
	public void setUp() throws Exception {
		relationDomReader = new DefaultRelationDomReader();
	}
	
	@Test
	public void testLoadXml() throws DocumentException {
		Relation relation = readEntry("relation.xml");
		assertNotNull(relation);
	}
	
	protected Relation readEntry(String path) throws DocumentException {
		org.w3c.dom.Element root = readDocument(path);
        
        assertEquals(Onesocialweb.RELATION_ELEMENT, root.getNodeName());
        assertEquals(Onesocialweb.NAMESPACE, root.getNamespaceURI());
        
        Relation relation = relationDomReader.readElement(root);
        assertNotNull(relation);
        
        return relation;
	}
	
	protected org.w3c.dom.Element readDocument(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream(path));
        return new ElementAdapter(document.getRootElement());
	}
}
