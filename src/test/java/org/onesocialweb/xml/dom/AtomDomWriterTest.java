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
import org.onesocialweb.model.activity.ActivityActor;
import org.onesocialweb.model.activity.ActivityFactory;
import org.onesocialweb.model.activity.ActivityObject;
import org.onesocialweb.model.atom.AtomCategory;
import org.onesocialweb.model.atom.AtomContent;
import org.onesocialweb.model.atom.AtomEntry;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.AtomPerson;
import org.onesocialweb.model.atom.DefaultAtomFactory;
import org.onesocialweb.model.atom.DefaultAtomHelper;
import org.onesocialweb.xml.dom.AtomDomWriter;
import org.onesocialweb.xml.dom.imp.DefaultAtomDomWriter;
import org.w3c.dom.Element;

public class AtomDomWriterTest {

	@Test
	public void entryToXML() throws DocumentException, IOException {
		AtomFactory factory = new DefaultAtomFactory();
		AtomEntry entry = factory.entry();
		entry.setId(DefaultAtomHelper.generateId());
		entry.setPublished(Calendar.getInstance().getTime());
		entry.setTitle("Just a test !");

		AtomPerson author = factory.person();
		author.setName("An author");
		entry.addAuthor(author);
		
		AtomPerson contributor = factory.person();
		contributor.setName("A contributor");
		contributor.setEmail("contrib@nowhere.com");
		entry.addContributor(contributor);
		
		AtomContent content = factory.content();
		content.setType("text/html");
		content.setValue("All your bases are belong to us.");
		entry.addContent(content);
		
		AtomCategory category = factory.category();
		category.setLabel("Testcase");
		category.setTerm("/test");
		entry.addCategory(category);
		
		AtomLink link = factory.link();
		link.setHref("http://eschnou.com");
		link.setRel("me");
		link.setTitle("My blog");
		entry.addLink(link);
		
		link = factory.link();
		link.setHref("mailto:me@eschnou.com");
		link.setRel("me");
		entry.addLink(link);

		DOMDocument document = new DOMDocument();
		AtomDomWriter atomDomWriter = new DefaultAtomDomWriter();
		Element element = atomDomWriter.toElement(entry, document);
		assertNotNull(element);
		
		DOMReader reader = new DOMReader();
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter( System.out, format );
	}
}
