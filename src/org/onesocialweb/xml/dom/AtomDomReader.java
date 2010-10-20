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
 *  2010-08-09 Modified by Luca Faggioli Copyright 2010 Openliven S.r.l.
 *  added filling of Link.count
 *
 */
package org.onesocialweb.xml.dom;

import java.util.Date;

import org.onesocialweb.model.atom.AtomCategory;
import org.onesocialweb.model.atom.AtomContent;
import org.onesocialweb.model.atom.AtomEntry;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.AtomPerson;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.xml.namespace.Atom;
import org.onesocialweb.xml.namespace.AtomThreading;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class AtomDomReader {

	private final AtomFactory factory;

	public AtomDomReader() {
		this.factory = getAtomFactory();
	}

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomReader#readEntry(org.w3c.dom.Element)
	 */
	public AtomEntry readEntry(Element element) {

		AtomEntry entry = factory.entry();

		entry.setId(DomHelper.getElementText(element, "id", Atom.NAMESPACE));
		entry.setTitle(DomHelper.getElementText(element, "title", Atom.NAMESPACE));
		entry.setPublished(parseDate(DomHelper.getElementText(element, "published", Atom.NAMESPACE)));
		entry.setUpdated(parseDate(DomHelper.getElementText(element, "updated", Atom.NAMESPACE)));

		// Get the authors
		NodeList authors = element.getElementsByTagNameNS(Atom.NAMESPACE, "author");
		for(int i=0; i < authors.getLength(); i++) {
			entry.addAuthor(readPerson((Element) authors.item(i)));
		}

		// Get the contributor
		NodeList contrib = element.getElementsByTagNameNS(Atom.NAMESPACE, "contributor");
		for(int i=0; i < contrib.getLength(); i++) {
			entry.addContributor(readPerson((Element) contrib.item(i)));
		}

		// Get the categories
		NodeList categories = element.getElementsByTagNameNS(Atom.NAMESPACE, "category");
		for(int i=0; i < categories.getLength(); i++) {
			entry.addCategory(readCategory((Element) categories.item(i)));
		}

		// Get the content
		NodeList contents = element.getElementsByTagNameNS(Atom.NAMESPACE, "content");
		for(int i=0; i < contents.getLength(); i++) {
			entry.addContent(readContent((Element) contents.item(i)));
		}

		// Get the links
		NodeList links = element.getElementsByTagNameNS(Atom.NAMESPACE, "link");
		for(int i=0; i < links.getLength(); i++) {
			entry.addLink(readLink((Element) links.item(i)));
		}

		// Get the reply-to
		NodeList replies = element.getElementsByTagNameNS(AtomThreading.NAMESPACE, AtomThreading.IN_REPLY_TO_ELEMENT);
		for(int i=0; i < replies.getLength(); i++) {
			entry.addRecipient(readReplyTo((Element) replies.item(i)));
		}

		return entry;

	}

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomReader#readCategory(org.w3c.dom.Element)
	 */
	public AtomCategory readCategory(Element element) {
		AtomCategory category = factory.category();
		category.setLabel(DomHelper.getElementAttribute(element, "label"));
		category.setScheme(DomHelper.getElementAttribute(element, "scheme"));
		category.setTerm(DomHelper.getElementAttribute(element, "term"));
		return category;
	}

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomReader#readLink(org.w3c.dom.Element)
	 */
	public AtomLink readLink(Element element) {
		AtomLink link = factory.link();
		link.setHref(DomHelper.getElementAttribute(element, "href"));
		link.setHreflang(DomHelper.getElementAttribute(element, "hreflang"));
		link.setLength(DomHelper.getElementAttribute(element, "length"));
		link.setRel(DomHelper.getElementAttribute(element, "rel"));
		link.setTitle(DomHelper.getElementAttribute(element, "title"));
		link.setType(DomHelper.getElementAttribute(element, "type"));
		try {
			link.setCount(Integer.parseInt(DomHelper.getElementAttribute(element, "thr:count")));
		} catch(NumberFormatException e) {
			try{ 
				link.setCount(Integer.parseInt(DomHelper.getElementAttribute(element, "count")));
			}
			catch(NumberFormatException ne) {
				link.setCount(0);
			}
			} 
					
		return link;
	}

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomReader#readPerson(org.w3c.dom.Element)
	 */
	public AtomPerson readPerson(Element element) {
		AtomPerson person = factory.person();
		person.setName(DomHelper.getElementText(element, Atom.NAME_ELEMENT, Atom.NAMESPACE));
		person.setEmail(DomHelper.getElementText(element, Atom.EMAIL_ELEMENT, Atom.NAMESPACE));
		person.setUri(DomHelper.getElementText(element, Atom.URI_ELEMENT, Atom.NAMESPACE));
		return person;
	}

	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomReader#readContent(org.w3c.dom.Element)
	 */
	public AtomContent readContent(Element element) {
		AtomContent content = factory.content();
		content.setType(DomHelper.getElementText(element, "type", Atom.NAMESPACE));
		content.setType(DomHelper.getElementText(element, "src", Atom.NAMESPACE));
		content.setValue(element.getTextContent().trim());
		return content;
	}

	public AtomReplyTo readReplyTo(Element element) {
		AtomReplyTo reply = factory.reply();;
		reply.setHref(DomHelper.getElementAttribute(element, "href"));
		reply.setRef(DomHelper.getElementAttribute(element, "ref"));
		reply.setType(DomHelper.getElementAttribute(element, "type"));
		reply.setSource(DomHelper.getElementAttribute(element, "source"));
		return reply;
	}

	protected abstract AtomFactory getAtomFactory();

	protected abstract Date parseDate(String atomDate);
}
