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
package org.onesocialweb.xml.xpp;

import java.io.IOException;
import java.util.Date;

import org.onesocialweb.model.activity.ActivityActor;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.model.activity.ActivityFactory;
import org.onesocialweb.model.activity.ActivityObject;
import org.onesocialweb.model.atom.AtomCategory;
import org.onesocialweb.model.atom.AtomContent;
import org.onesocialweb.model.atom.AtomEntry;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomLink;
import org.onesocialweb.model.atom.AtomPerson;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.model.atom.AtomSource;
import org.onesocialweb.xml.namespace.Activitystreams;
import org.onesocialweb.xml.namespace.Atom;
import org.onesocialweb.xml.namespace.AtomThreading;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class XppActivityReader implements XppReader<ActivityEntry> {

	private final ActivityFactory activityFactory;
	
	private final AtomFactory atomFactory;
	
	private final XppAclReader aclReader;

	public XppActivityReader() {
		this.aclReader = getXppAclReader();
		this.activityFactory = getActivityFactory();
		this.atomFactory = getAtomFactory();
	}

	@Override
	public ActivityEntry parse(XmlPullParser parser) throws XmlPullParserException, IOException {

		// Verify that we are on the right token
		if (!(parser.getNamespace().equals(Atom.NAMESPACE) 
				&& parser.getName().equals(Atom.ENTRY_ELEMENT))) {
			throw new XmlPullParserException("Unexpected token " + parser);
		}

		//Proceed with parsing
		final ActivityEntry entry = activityFactory.entry();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(Activitystreams.NAMESPACE)) {
					if (name.equals(Activitystreams.ACTOR_ELEMENT)) {
						entry.setActor(parseActor(parser));
					} else if (name.equals(Activitystreams.VERB_ELEMENT)) {
						entry.addVerb(activityFactory.verb(parser.nextText()));
					} else if (name.equals(Activitystreams.OBJECT_ELEMENT)) {
						entry.addObject(parseObject(parser));
					}
				} else if (namespace.equals(Atom.NAMESPACE)) {
					readAtomEntryElement(entry, parser);
				} else if (namespace.equals(AtomThreading.NAMESPACE)) {
					readAtomThreadingElement(entry, parser);
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						entry.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(Atom.NAMESPACE)
						&& name.equals(Atom.ENTRY_ELEMENT)) {
					done = true;
				}
			}
		}
		
		if (entry.hasRecipients()){
			for (AtomReplyTo replyto: entry.getRecipients()){
				if ((replyto.getRef()!=null) && (replyto.getHref().contains("?;node=urn:"))){
					entry.setParentId(readParentId(replyto.getHref()));										
					entry.setParentJID(readParentJID(replyto.getHref()));
				}					
			}
		}
		return entry;
	}
	
	protected ActivityActor parseActor(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final ActivityActor actor = activityFactory.actor();
		readAtomPersonElement(actor, parser);
		return actor;
	}

	/**
	 * @param parser
	 * @return A new AtomPerson
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	protected AtomPerson parsePerson(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final AtomPerson person = atomFactory.person();
		readAtomPersonElement(person, parser);
		return person;
	}
	
	/**
	 * Populates the passed AtomPerson with information from the provided
	 * parser.
	 *
	 * @param person
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	protected void readAtomPersonElement(AtomPerson person, XmlPullParser parser) throws XmlPullParserException, IOException {
		final String tagName = parser.getName();
		final String tagNamespace = parser.getNamespace();
		boolean done = false;
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(Atom.NAMESPACE)) {
					if (name.equals(Atom.NAME_ELEMENT)) {
						person.setName(parser.nextText().trim());
					} else if (name.equals(Atom.EMAIL_ELEMENT)) {
						person.setEmail(parser.nextText().trim());
					}  else if (name.equals(Atom.URI_ELEMENT)) {
						person.setUri(parser.nextText().trim());
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(tagNamespace) && name.equals(tagName)) {
					done = true;
				}
			}
		}
	}
	
	protected AtomContent parseContent(XmlPullParser parser) throws XmlPullParserException, IOException {
		final AtomContent content = atomFactory.content();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(Atom.SRC_ATTRIBUTE)) {
				content.setSrc(value);
			} else if (name.equals(Atom.TYPE_ATTRIBUTE)) {
				content.setType(value);
			}
		}
		
		String text = parser.nextText().trim();
		if (text.length() > 0) {
			content.setValue(text);
		}
		
		return content;
	}
	
	protected AtomCategory parseCategory(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final AtomCategory category = atomFactory.category();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(Atom.LABEL_ATTRIBUTE)) {
				category.setLabel(value);
			} else if (name.equals(Atom.SCHEME_ATTRIBUTE)) {
				category.setScheme(value);
			} else if (name.equals(Atom.TERM_ATTRIBUTE)) {
				category.setTerm(value);
			}
		}
		return category;
	}
	
	protected AtomSource parseSource(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final AtomSource source = atomFactory.source();
		// TODO
		return source;
	}
	
	protected AtomLink parseLink(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final AtomLink link = atomFactory.link();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);  
			
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(Atom.HREF_ATTRIBUTE)) {
				link.setHref(value);
			} else if (name.equals(Atom.HREFLANG_ATTRIBUTE)) {
				link.setHreflang(value);
			} else if (name.equals(Atom.LENGTH_ATTRIBUTE)) {
				link.setLength(value);
			}  else if (name.equals(Atom.REL_ATTRIBUTE)) {
				link.setRel(value);
			}  else if (name.equals(Atom.TITLE_ATTRIBUTE)) {
				link.setTitle(value);
			}  else if (name.equals(Atom.TYPE_ATTRIBUTE)) {
				link.setType(value);
			} else { 
				String prefix=parser.getAttributePrefix(i);	
				String qualifiedName=prefix+":"+name;
				if (qualifiedName.equalsIgnoreCase(AtomThreading.COUNT)){
					link.setCount(Integer.parseInt(value));
				}
			}
		}
		return link;
	}
	
	protected AtomReplyTo parseRecipient(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final AtomReplyTo recipient = atomFactory.reply();
		for (int i=0; i<parser.getAttributeCount(); i++) {
			String name = parser.getAttributeName(i);
			String value = parser.getAttributeValue(i).trim();
			if (name.equals(AtomThreading.HREF_ATTRIBUTE)) {
				recipient.setHref(value);
			}  else if (name.equals(AtomThreading.TYPE_ATTRIBUTE)) {
				recipient.setType(value);
			}  else if (name.equals(AtomThreading.SOURCE_ATTRIBUTE)) {
				recipient.setSource(value);
			}  else if (name.equals(AtomThreading.REF_ATTRIBUTE)) {
				recipient.setRef(value);
			}
		}
		return recipient;
	}
	
	protected void readAtomEntryElement(AtomEntry entry, XmlPullParser parser) throws XmlPullParserException, IOException {
		String name = parser.getName();
		
		if (name.equals(Atom.AUTHOR_ELEMENT)) {
			entry.addAuthor(parsePerson(parser));
		} else if (name.equals(Atom.CONTRIBUTOR_ELEMENT)) {
			entry.addContributor(parsePerson(parser));
		} else if (name.equals(Atom.CONTENT_ELEMENT)) {
			entry.addContent(parseContent(parser));
		} else if (name.equals(Atom.LINK_ELEMENT)) {
			entry.addLink(parseLink(parser));
		} else if (name.equals(Atom.CATEGORY_ELEMENT)) {
			entry.addCategory(parseCategory(parser));
		} else if (name.equals(Atom.ID_ELEMENT)) {
			entry.setId(parser.nextText().trim());
		} else if (name.equals(Atom.PUBLISHED_ELEMENT)) {
			entry.setPublished(parseDate(parser.nextText().trim()));
		} else if (name.equals(Atom.UPDATED_ELEMENT)) {
			entry.setUpdated(parseDate(parser.nextText().trim()));
		} else if (name.equals(Atom.TITLE_ELEMENT)) {
			entry.setTitle(parser.nextText().trim());
		}
	}
	
	protected void readAtomThreadingElement(AtomEntry entry, XmlPullParser parser) throws XmlPullParserException, IOException {
		String name = parser.getName();
		
		if (name.equals(AtomThreading.IN_REPLY_TO_ELEMENT)) {
			entry.addRecipient(parseRecipient(parser));
		}
	}
	
	protected ActivityObject parseObject(XmlPullParser parser) throws XmlPullParserException, IOException {
		final ActivityObject object = activityFactory.object();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(Activitystreams.NAMESPACE)) {
					if (name.equals(Activitystreams.OBJECT_TYPE_ELEMENT)) {
						object.setType(parser.nextText().trim());
					}
				} else if (namespace.equals(Atom.NAMESPACE)) {
					readAtomEntryElement(object, parser);
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					//
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(Activitystreams.NAMESPACE)
						&& name.equals(Activitystreams.OBJECT_ELEMENT)) {
					done = true;
				}
			}
		}
		return object;
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

	abstract protected ActivityFactory getActivityFactory();
	
	abstract protected AtomFactory getAtomFactory();
	
	abstract protected XppAclReader getXppAclReader();
	
	abstract protected Date parseDate(String atomDate);
	
}
