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
package org.onesocialweb.model.atom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultAtomEntry extends DefaultAtomCommon implements AtomEntry {

	private List<AtomPerson> authors = new ArrayList<AtomPerson>();

	private List<AtomCategory> categories = new ArrayList<AtomCategory>();

	private List<AtomContent> contents = new ArrayList<AtomContent>();

	private List<AtomPerson> contributors = new ArrayList<AtomPerson>();
	
	private List<AtomReplyTo> recipients = new ArrayList<AtomReplyTo>();

	private String id;
	
	private String parentId=null;
	
	private String parentJID=null;
	

	private List<AtomLink> links = new ArrayList<AtomLink>();

	private Date published;

	private String rights;

	private AtomSource source;

	private String title;

	private Date updated;


	@Override
	public void addAuthor(AtomPerson author) {
		this.authors.add(author);
	}

	@Override
	public void addCategory(AtomCategory category) {
		this.categories.add(category);
	}

	@Override
	public void addContent(AtomContent content) {
		this.contents.add(content);
	}

	@Override
	public void addContributor(AtomPerson person) {
		this.contributors.add(person);
	}

	@Override
	public void addLink(AtomLink link) {
		this.links.add(link);
	}
	
	@Override
	public void addRecipient(AtomReplyTo to) {
		this.recipients.add(to);
	}
	

	
	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public void setParentJID(String parentJID) {
		this.parentJID = parentJID;
	}
	
	@Override
	public String getParentJID() {
		return parentJID;
	}

	@Override
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public List<AtomPerson> getAuthors() {
		return authors;
	}

	@Override
	public List<AtomCategory> getCategories() {
		return categories;
	}

	@Override
	public List<AtomContent> getContents() {
		return contents;
	}

	@Override
	public List<AtomPerson> getContributors() {
		return contributors;
	}
	
	@Override
	public List<AtomReplyTo> getRecipients() {
		return recipients;
	}
	
	@Override
	public AtomLink getRepliesLink(){
		for (AtomLink link: links){
			if (link.getRel().equalsIgnoreCase("replies"))
				return link;
		}
		return null;
	}
	
	@Override
	public AtomReplyTo getReplyTo(){
		for (AtomReplyTo reply: recipients){
			if (reply.getRef()!=null && (reply.getRef().length()>0))
				return reply;
		}
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<AtomLink> getLinks() {
		return links;
	}

	@Override
	public Date getPublished() {
		return published;
	}

	@Override
	public String getRights() {
		return rights;
	}

	@Override
	public AtomSource getSource() {
		return source;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Date getUpdated() {
		return updated;
	}

	@Override
	public boolean hasAuthors() {
		return (authors != null && authors.size() > 0);
	}

	@Override
	public boolean hasCategories() {
		return (categories != null && categories.size() > 0);
	}

	@Override
	public boolean hasContents() {
		return (contents != null && contents.size() > 0);
	}

	@Override
	public boolean hasContributors() {
		return (contributors != null && contributors.size() > 0);
	}
	
	@Override
	public boolean hasReplies() {
		if (!(links == null || links.size() <= 0)) {
			for (AtomLink link: links) {
				String rel = link.getRel();
				if (rel != null && rel.equalsIgnoreCase("replies"))
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasId() {
		return id != null;
	}

	@Override
	public boolean hasLinks() {
		return (links != null && links.size() > 0);
	}

	@Override
	public boolean hasPublished() {
		return published != null;
	}

	@Override
	public boolean hasRights() {
		return rights != null;
	}

	@Override
	public boolean hasSource() {
		return source != null;
	}

	@Override
	public boolean hasTitle() {
		return title != null;
	}

	@Override
	public boolean hasUpdated() {
		return updated != null;
	}
	
	@Override
	public boolean hasRecipients() {
		return (recipients != null && recipients.size() > 0);
	}

	@Override
	public void removeAuthor(AtomPerson author) {
		authors.remove(author);
	}

	@Override
	public void removeCategory(AtomCategory category) {
		categories.remove(category);
	}

	@Override
	public void removeContent(AtomContent content) {
		contents.remove(content);
	}

	@Override
	public void removeContributor(AtomPerson person) {
		contributors.remove(person);
	}

	@Override
	public void removeLink(AtomLink link) {
		links.remove(link);
	}

	@Override
	public void removeRecipient(AtomReplyTo recipient) {
		recipients.remove(recipient);
	}
	
	@Override
	public void setAuthors(final List<AtomPerson> authors) {
		this.authors = authors;
	}

	@Override
	public void setCategories(final List<AtomCategory> categories) {
		this.categories = categories;
	}

	@Override
	public void setContents(final List<AtomContent> contents) {
		this.contents = contents;
	}

	@Override
	public void setContributors(final List<AtomPerson> contributors) {
		this.contributors = contributors;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setLinks(final List<AtomLink> links) {
		this.links = links;
	}

	@Override
	public void setPublished(final Date published) {
		this.published = published;
	}

	@Override
	public void setRights(final String rights) {
		this.rights = rights;
	}

	@Override
	public void setSource(final AtomSource source) {
		this.source = source;
	}

	@Override
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public void setUpdated(final Date updated) {
		this.updated = updated;
	}
	
	@Override
	public void setRecipients(final List<AtomReplyTo> recipients) {
		this.recipients = recipients;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[AtomEntry ");
		if (id != null) {
			buffer.append("id:" + id + " ");
		}
		if (published != null) {
			buffer.append("published:" + published + " ");
		}
		if (updated != null) {
			buffer.append("updated:" + updated + " ");
		}
		if (title != null) {
			buffer.append("title:" + title + " ");
		}
		for (AtomPerson atomPerson : authors) {
			buffer.append(atomPerson.toString());
		}
		for (AtomPerson atomPerson : contributors) {
			buffer.append(atomPerson.toString());
		}
		for (AtomCategory category : categories) {
			buffer.append(category.toString());
		}
		for (AtomLink atomLink : links) {
			buffer.append(atomLink.toString());
		}
		for (AtomContent atomContent : contents) {
			buffer.append(atomContent.toString());
		}
		for (AtomReplyTo atomRecipient : recipients) {
			buffer.append(atomRecipient.toString());
		}
		buffer.append("]");
		return buffer.toString();
	}
}
