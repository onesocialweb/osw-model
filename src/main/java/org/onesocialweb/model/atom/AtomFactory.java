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

public abstract class AtomFactory {
	
	public abstract AtomCategory category();
	
	public abstract AtomContent content();

	public abstract AtomEntry entry();
	
	public abstract AtomLink link();
	
	public abstract AtomReplyTo reply();
	
	public abstract AtomPerson person();
	
	public abstract AtomSource source();
	
	public AtomCategory category(String term, String label, String scheme) {
		AtomCategory category = category();
		category.setTerm(term);
		category.setLabel(label);
		category.setScheme(scheme);
		return category;
	}
	
	public AtomContent content(String value, String type, String src) {
		AtomContent content = content();
		content.setValue(value);
		content.setType(type);
		content.setSrc(src);
		return content;
	}
	
	public AtomLink link(String href, String rel, String title, String type, int count) {
		AtomLink link = link();
		link.setHref(href);
		link.setRel(rel);
		link.setTitle(title);
		link.setType(type);
		link.setCount(count);
		return link;
	}
	
	public AtomLink link(String href, String rel, String title, String type) {
		AtomLink link = link();
		link.setHref(href);
		link.setRel(rel);
		link.setTitle(title);
		link.setType(type);
		return link;
	}
	
	public AtomReplyTo reply( String ref, String href, String type, String source) {
		AtomReplyTo reply = reply();
		reply.setRef(ref);
		reply.setHref(href);
		reply.setType(type);
		reply.setSource(source);
		return reply;
	}
	
	public AtomPerson person(String email, String name, String uri) {
		AtomPerson person = person();
		person.setEmail(email);
		person.setName(name);
		person.setUri(uri);
		return person;
	}
	
}
