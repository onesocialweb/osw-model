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

import java.io.IOException;
import java.util.Calendar;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.acl.DefaultAclFactory;
import org.onesocialweb.model.activity.ActivityActor;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.model.activity.ActivityFactory;
import org.onesocialweb.model.activity.ActivityObject;
import org.onesocialweb.model.activity.DefaultActivityFactory;
import org.onesocialweb.model.atom.AtomCategory;
import org.onesocialweb.model.atom.AtomContent;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.DefaultAtomFactory;
import org.onesocialweb.model.atom.DefaultAtomHelper;
import org.onesocialweb.xml.writer.ActivityXmlWriter;

public class ActivityXmlWriterTest {

	@Test
	public void entryToXML() throws DocumentException, IOException {
		AtomFactory atomFactory = new DefaultAtomFactory();
		ActivityFactory activityFactory = new DefaultActivityFactory();
		AclFactory aclFactory = new DefaultAclFactory();
		
		ActivityEntry entry = activityFactory.entry();
		entry.setId(DefaultAtomHelper.generateId());
		entry.setPublished(Calendar.getInstance().getTime());
		entry.setTitle("My first status update");

		ActivityActor author = activityFactory.actor();
		author.setName("Alice & June");
		author.setUri("eschnou@onesocial.me");
		entry.setActor(author);
		
		ActivityObject object = activityFactory.object();
		object.setTitle("My first <bold>status</bold> update");
		object.setType(ActivityObject.STATUS_UPDATE);
		entry.addObject(object);
		
		AtomContent content = atomFactory.content();
		content.setType("text/html");
		content.setValue("My first status update (in Html this time :-)");
		object.addContent(content);
		
		AtomCategory category = atomFactory.category();
		category.setLabel("Testcase");
		category.setTerm("/test");
		object.addCategory(category);
		
		AtomLink link = atomFactory.link();
		link.setHref("http://eschnou.com");
		link.setRel("me");
		link.setTitle("My blog");
		object.addLink(link);
		
		AclRule rule = aclFactory.aclRule();
		rule.addAction(aclFactory.aclAction(AclAction.ACTION_VIEW, AclAction.PERMISSION_GRANT));
		rule.addSubject(aclFactory.aclSubject(null, AclSubject.EVERYONE));
		entry.addAclRule(rule);
		
		//Very simple stuff...
		ActivityXmlWriter writer=new ActivityXmlWriter();
				
	}
}
