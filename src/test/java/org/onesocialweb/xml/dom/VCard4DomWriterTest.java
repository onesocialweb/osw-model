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
import java.util.ArrayList;
import java.util.List;

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
import org.onesocialweb.model.vcard4.DefaultVCard4Factory;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.Field;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.TelField;
import org.onesocialweb.model.vcard4.VCard4Factory;
import org.onesocialweb.model.vcard4.exception.CardinalityException;
import org.onesocialweb.model.vcard4.exception.UnsupportedFieldException;
import org.onesocialweb.xml.dom.VCard4DomWriter;
import org.onesocialweb.xml.dom.imp.DefaultVCard4DomWriter;
import org.w3c.dom.Element;

public class VCard4DomWriterTest {

	@Test
	public void entryToXML() throws DocumentException, IOException {
		VCard4Factory profileFactory = new DefaultVCard4Factory();
		AclFactory aclFactory = new DefaultAclFactory();

		List<AclRule> rules = new ArrayList<AclRule>();
		AclRule rule = aclFactory.aclRule();
		rule.addAction(aclFactory.aclAction(AclAction.ACTION_VIEW,
				AclAction.PERMISSION_GRANT));
		rule.addSubject(aclFactory.aclSubject(null, AclSubject.EVERYONE));
		rules.add(rule);

		Profile profile = profileFactory.profile();
		profile.setUserId("test@xmpp.loc");
		Field fnField = profileFactory.fullname("Alice in Wonderland");
		fnField.setAclRules(rules);


		Field sexField = profileFactory.gender(GenderField.Type.FEMALE);
		sexField.setAclRules(rules);


		Field noteField = profileFactory.note("Lost in a land of wonders");
		noteField.setAclRules(rules);

		Field photoField = profileFactory.photo("http://wonderland.lit/alice.jpg");
		photoField.setAclRules(rules);
		
		Field emailField = profileFactory.email("diana.cheng@gmail.com");
		emailField.setAclRules(rules);
		
		Field nameField = profileFactory.name("Prof. Dr.", "Otto", "Schumacher", "Jr.");
		nameField.setAclRules(rules);
		
		Field telField= profileFactory.tel("+4917667027845", TelField.Type.CELL);
		telField.setAclRules(rules);
		
		Field tzField= profileFactory.timeZone("Europe/Berlin");		
		tzField.setAclRules(rules);
		
		Field urlField=profileFactory.url("http://www.flickr.com/photos/dianacheng");
		urlField.setAclRules(rules);
		
		try {
			profile.addField(fnField);
			profile.addField(sexField);
			profile.addField(photoField);
			profile.addField(noteField);
			profile.addField(emailField);
			profile.addField(telField);
			profile.addField(tzField);
			profile.addField(urlField);
			
		} catch (CardinalityException e) {
		} catch (UnsupportedFieldException e) {	
		}
		
		DOMDocument document = new DOMDocument();
		VCard4DomWriter writer = new DefaultVCard4DomWriter();
		Element element = writer.toElement(profile, document);
		assertNotNull(element);

		DOMReader reader = new DOMReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter = new XMLWriter(System.out, format);
	}
}
