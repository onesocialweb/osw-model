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
import org.dom4j.io.DOMWriter;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.xml.dom.VCard4DomReader;
import org.onesocialweb.xml.dom.imp.DefaultVCard4DomReader;
import org.onesocialweb.xml.namespace.VCard4;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VCard4DomReaderTest {

	private VCard4DomReader vcard4DomReader;

	@Before
	public void setUp() throws Exception {
		vcard4DomReader = new DefaultVCard4DomReader();
	}
	
	@Test
	public void testLoadProfile() throws DocumentException {
		Profile profile = readProfile("vcard4.xml");
		assertNotNull(profile);
		
		// Add a fictive user id
		profile.setUserId("alice@wonderland.it");
		
	}
	
	protected Profile readProfile(String path) throws DocumentException {
		org.w3c.dom.Document document = readDocument(path);
        Element root = (Element) document.getFirstChild();
        
        assertEquals("vcards", root.getNodeName());
        assertEquals(VCard4.NAMESPACE, root.getNamespaceURI());
        
        NodeList vcardNodes = root.getElementsByTagName(VCard4.VCARD_ELEMENT);
        assert(vcardNodes.getLength() > 0);
                
        return vcard4DomReader.readProfile((Element) vcardNodes.item(0));
	}
	
	protected org.w3c.dom.Document readDocument(String path) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream(path));       
        DOMWriter	writer = new DOMWriter();            	       
        return writer.write(document);	
	}

}
