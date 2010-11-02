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

import java.util.Date;

import org.onesocialweb.model.activity.ActivityActor;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.model.activity.ActivityFactory;
import org.onesocialweb.model.activity.ActivityObject;
import org.onesocialweb.model.activity.ActivityVerb;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.xml.namespace.Activitystreams;
import org.onesocialweb.xml.namespace.Atom;
import org.onesocialweb.xml.namespace.AtomThreading;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class ActivityDomReader {

	private final ActivityFactory factory;

	private final AclDomReader aclDomReader;

	private final AtomDomReader atomDomReader;

	public ActivityDomReader() {
		this.factory = getActivityFactory();
		this.aclDomReader = getAclDomReader();
		this.atomDomReader = getAtomDomReader();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.onesocialweb.model.activity.ActivityDomReader#readEntry(org.w3c.dom
	 * .Element)
	 */
	public ActivityEntry readEntry(Element element) {
		ActivityEntry entry = factory.entry();

		// The atom stuff
		entry.setId(DomHelper.getElementText(element, "id", Atom.NAMESPACE));
		entry.setTitle(DomHelper.getElementText(element, "title", Atom.NAMESPACE));
		entry.setPublished(parseDate(DomHelper.getElementText(element, "published", Atom.NAMESPACE)));
		entry.setUpdated(parseDate(DomHelper.getElementText(element, "updated", Atom.NAMESPACE)));

		// Get the actor (ignore if more than one)
		Element actor = DomHelper.getElement(element, "actor", Activitystreams.NAMESPACE);
		if (actor != null) {
			entry.setActor(readActor(actor));
		}

		// Get the verbs
		NodeList verbs = element.getElementsByTagNameNS(Activitystreams.NAMESPACE, "verb");
		for (int i = 0; i < verbs.getLength(); i++) {
			entry.addVerb(readVerb((Element) verbs.item(i)));
		}

		// Get the objects
		NodeList objects = element.getElementsByTagNameNS(Activitystreams.NAMESPACE, "object");
		for (int i = 0; i < objects.getLength(); i++) {
			entry.addObject(readObject((Element) objects.item(i)));
		}

		// Get the acl rules
		NodeList rules = element.getElementsByTagNameNS(Onesocialweb.NAMESPACE, "acl-rule");
		for (int i = 0; i < rules.getLength(); i++) {
			entry.addAclRule(aclDomReader.readRule((Element) rules.item(i)));
		}

		// Get the reply-to
		NodeList replies = element.getElementsByTagNameNS(AtomThreading.NAMESPACE, AtomThreading.IN_REPLY_TO_ELEMENT);
		for(int i=0; i < replies.getLength(); i++) {
			AtomReplyTo replyTo= atomDomReader.readReplyTo((Element) replies.item(i));
			if (replyTo.getHref()!=null){
				entry.setParentId(readParentId(replyTo.getHref()));
				entry.setParentJID(readParentJID(replyTo.getHref()));
			}
			entry.addRecipient(replyTo);
		}

		//Get the links...
		NodeList links = element.getElementsByTagNameNS(Atom.NAMESPACE, Atom.LINK_ELEMENT);
		for(int i=0; i < links.getLength(); i++) {
			AtomLink link= atomDomReader.readLink(((Element) links.item(i)));
			entry.addLink(link);
		}


		return entry;
	}

	public String readParentJID(String href)
	{
		if (href.length()==0)
			return null;
		int i=href.indexOf("?");
		if(i == -1) {
			return null;
		}
		return href.substring(5, i);

	}

	public String readParentId(String href)
	{
		if (href.length()==0)
			return null;
		int i=href.indexOf("item=");
		if(i == -1) {
			return null;
		}
		return href.substring(5+i, href.length());

	}

	public String readActivityId(Element element) {
		return DomHelper.getElementAttribute(element, "id");

	}

	protected ActivityActor readActor(Element element) {
		ActivityActor actor = factory.actor();
		actor.setUri(DomHelper.getElementText(element, "uri", Atom.NAMESPACE));
		actor.setName(DomHelper.getElementText(element, "name", Atom.NAMESPACE));
		actor.setEmail(DomHelper.getElementText(element, "email", Atom.NAMESPACE));
		return actor;
	}

	protected ActivityVerb readVerb(Element element) {
		ActivityVerb verb = factory.verb();
		verb.setValue(element.getTextContent().trim());
		return verb;
	}

	protected ActivityObject readObject(Element element) {
		ActivityObject object = factory.object();

		object.setId(DomHelper.getElementText(element, "id", Atom.NAMESPACE));
		object.setType(DomHelper.getElementText(element, "object-type", Activitystreams.NAMESPACE));
		object.setTitle(DomHelper.getElementText(element, "title", Atom.NAMESPACE));
		object.setPublished(parseDate(DomHelper.getElementText(element, "published", Atom.NAMESPACE)));
		object.setUpdated(parseDate(DomHelper.getElementText(element, "updated", Atom.NAMESPACE)));

		// content
		NodeList contents = element.getElementsByTagNameNS(Atom.NAMESPACE, "content");
		for (int i = 0; i < contents.getLength(); i++) {
			object.addContent(atomDomReader.readContent((Element) contents.item(i)));
		}

		// links
		NodeList links = element.getElementsByTagNameNS(Atom.NAMESPACE, "link");
		for (int i = 0; i < links.getLength(); i++) {
			object.addLink(atomDomReader.readLink((Element) links.item(i)));
		}

		return object;
	}

	protected abstract ActivityFactory getActivityFactory();

	protected abstract AclDomReader getAclDomReader();

	protected abstract AtomDomReader getAtomDomReader();

	protected abstract Date parseDate(String atomDate);

}
