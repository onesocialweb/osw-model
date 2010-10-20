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

import static org.onesocialweb.xml.dom.DomHelper.appendTextNode;
import static org.onesocialweb.xml.dom.DomHelper.setTextContent;

import java.util.Date;

import org.onesocialweb.model.atom.AtomCategory;
import org.onesocialweb.model.atom.AtomContent;
import org.onesocialweb.model.atom.AtomEntry;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.AtomPerson;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.xml.namespace.Atom;
import org.onesocialweb.xml.namespace.AtomThreading;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public abstract class AtomDomWriter {
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#toElement(org.onesocialweb.model.atom.AtomEntry, org.w3c.dom.Document)
	 */
	public Element toElement(AtomEntry entry, Document document) {
		Element root = document.createElementNS(Atom.NAMESPACE, Atom.ENTRY_ELEMENT);
		document.appendChild(root);
		write(entry, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#toElement(org.onesocialweb.model.atom.AtomEntry, org.w3c.dom.Element)
	 */
	public Element toElement(AtomEntry entry, Element parent) {
		Element root = (Element) parent.appendChild(parent.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.ENTRY_ELEMENT));
		write(entry, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomEntry, org.w3c.dom.Element)
	 */
	public void write(AtomEntry entry, Element target) {
		
		if (entry.hasId()) appendTextNode(target, Atom.NAMESPACE, Atom.ID_ELEMENT, entry.getId());
		if (entry.hasPublished()) appendTextNode(target, Atom.NAMESPACE, "published", format(entry.getPublished()));
		if (entry.hasUpdated()) appendTextNode(target, Atom.NAMESPACE, "updated", format(entry.getUpdated()));
		if (entry.hasTitle()) appendTextNode(target, Atom.NAMESPACE, "title", entry.getTitle());
		
		if (entry.hasAuthors()) {
			for (AtomPerson author : entry.getAuthors()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.AUTHOR_ELEMENT));
				write(author, e);
			}
		}

		if (entry.hasCategories()) {
			for (AtomCategory category : entry.getCategories()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.CATEGORY_ELEMENT));
				write(category, e);
			}
		}		

		if (entry.hasContents()) {
			for (AtomContent content : entry.getContents()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.CONTENT_ELEMENT));
				write(content, e);
			}
		}
		
		if (entry.hasContributors()) {
			for (AtomPerson contributor : entry.getContributors()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.CONTRIBUTOR_ELEMENT));
				write(contributor, e);
			}
		}
		
		if (entry.hasLinks()) {
			for (AtomLink link : entry.getLinks()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.LINK_ELEMENT));
				if (entry.hasReplies())
					write(link, e, entry.getRepliesLink().getCount());
				else 
					write(link, e, 0);
			}
		}
		
		if (entry.hasRecipients()) {
			for (AtomReplyTo recipient : entry.getRecipients()) {
				Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(AtomThreading.NAMESPACE, AtomThreading.IN_REPLY_TO_ELEMENT));
				write(recipient, e);
			}
		}
		
		if ((entry.getParentId()!=null) && (entry.getParentJID()!=null)){
			
			Element e1 = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.LINK_ELEMENT));
			e1.setAttribute(Atom.REL_ATTRIBUTE, "alternate");
			e1.setAttribute(Atom.TYPE_ATTRIBUTE, "text/html");
			e1.setAttribute(Atom.HREF_ATTRIBUTE, "xmpp:"+entry.getParentJID()+"?;node=urn:xmpp:microblog:0;item="+entry.getParentId());
			
			Element e2 = (Element) target.appendChild(target.getOwnerDocument().createElementNS(AtomThreading.NAMESPACE, AtomThreading.IN_REPLY_TO_ELEMENT));
			e2.setAttribute(AtomThreading.REF_ATTRIBUTE, entry.getParentId());
			e2.setAttribute(AtomThreading.HREF_ATTRIBUTE, "xmpp:"+entry.getParentJID()+"?;node=urn:xmpp:microblog:0;item="+entry.getParentId());			
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomCategory, org.w3c.dom.Element)
	 */
	public void write(AtomCategory category, Element target) {
		if (category.hasLabel()) target.setAttribute(Atom.LABEL_ATTRIBUTE, category.getLabel());
		if (category.hasScheme()) target.setAttribute(Atom.SCHEME_ATTRIBUTE, category.getScheme());
		if (category.hasTerm())	target.setAttribute(Atom.TERM_ATTRIBUTE, category.getTerm());
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomContent, org.w3c.dom.Element)
	 */
	public void write(AtomContent content, Element target) {
		if (content.hasSrc()) target.setAttribute(Atom.SRC_ATTRIBUTE, content.getSrc());
		if (content.hasType()) target.setAttribute(Atom.TYPE_ATTRIBUTE, content.getType());
		if (content.hasValue()) setTextContent(target, content.getValue());		
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomLink, org.w3c.dom.Element)
	 */
	public void write(AtomLink link, Element target, int replies) {
		if (link.hasHref()) target.setAttribute(Atom.HREF_ATTRIBUTE, link.getHref());
		if (link.hasHreflang()) target.setAttribute(Atom.HREFLANG_ATTRIBUTE, link.getHreflang());
		if (link.hasLength()) target.setAttribute(Atom.LENGTH_ATTRIBUTE, link.getLength());
		if (link.hasRel()) target.setAttribute(Atom.REL_ATTRIBUTE, link.getRel());
		if (link.hasTitle()) target.setAttribute(Atom.TITLE_ATTRIBUTE, link.getTitle());
		if (link.hasType()) target.setAttribute(Atom.TYPE_ATTRIBUTE, link.getType());	
		if (replies>0)			
			target.setAttributeNS(AtomThreading.NAMESPACE, AtomThreading.COUNT, ""+replies);
	}
		
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomPerson, org.w3c.dom.Element)
	 */
	public void write(AtomPerson person,  Element target) {
		if (person.hasEmail()) appendTextNode(target, Atom.NAMESPACE, Atom.EMAIL_ELEMENT, person.getEmail());
		if (person.hasName()) appendTextNode(target, Atom.NAMESPACE, Atom.NAME_ELEMENT, person.getName());
		if (person.hasUri()) appendTextNode(target, Atom.NAMESPACE, Atom.URI_ELEMENT, person.getUri());		
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.atom.AtomDomWriter#write(org.onesocialweb.model.atom.AtomReplyTo, org.w3c.dom.Element)
	 */
	public void write(AtomReplyTo recipient, Element target) {
		if (recipient.hasRef()) target.setAttribute(AtomThreading.REF_ATTRIBUTE, recipient.getRef());
		if (recipient.hasHref()) target.setAttribute(AtomThreading.HREF_ATTRIBUTE, recipient.getHref());
		if (recipient.hasType()) target.setAttribute(AtomThreading.TYPE_ATTRIBUTE, recipient.getType());
		if (recipient.hasSource()) target.setAttribute(AtomThreading.SOURCE_ATTRIBUTE, recipient.getSource());
	}
	
	protected abstract String format(Date date);
}
