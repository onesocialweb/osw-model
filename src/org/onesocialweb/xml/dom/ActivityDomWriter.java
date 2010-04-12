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

import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.activity.ActivityActor;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.model.activity.ActivityObject;
import org.onesocialweb.model.activity.ActivityVerb;
import org.onesocialweb.xml.namespace.Activitystreams;
import org.onesocialweb.xml.namespace.Atom;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ActivityDomWriter {
		
	private final AtomDomWriter atomDomWriter;
	
	private final AclDomWriter aclDomWriter;

	public ActivityDomWriter() {
		this.atomDomWriter = getAtomDomWriter();
		this.aclDomWriter = getAclDomWriter();
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#toElement(org.onesocialweb.model.activity.ActivityEntry, org.w3c.dom.Document)
	 */
	public Element toElement(ActivityEntry entry, Document document) {
		Element root = document.createElementNS(Atom.NAMESPACE, Atom.ENTRY_ELEMENT);
		document.appendChild(root);
		write(entry, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#toElement(org.onesocialweb.model.activity.ActivityEntry, org.w3c.dom.Element)
	 */
	public Element toElement(ActivityEntry entry, Element parent) {
		Element root = (Element) parent.appendChild(parent.getOwnerDocument().createElementNS(Atom.NAMESPACE, Atom.ENTRY_ELEMENT));
		write(entry, root);
		return root;
	}
		
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#write(org.onesocialweb.model.activity.ActivityEntry, org.w3c.dom.Element)
	 */
	public void write(ActivityEntry entry, Element target) {		
		atomDomWriter.write(entry, target);
		
		if (entry.hasAclRules()) {
			for (AclRule rule : entry.getAclRules()) {
				Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Onesocialweb.NAMESPACE, Onesocialweb.ACL_RULE_ELEMENT));
				aclDomWriter.write(rule, element);
			}
		}
		if (entry.hasActor()) {
			Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Activitystreams.NAMESPACE, Activitystreams.ACTOR_ELEMENT));
			write(entry.getActor(), element);

		}
		if (entry.hasObjects()) {
			for (ActivityObject object : entry.getObjects()) {
				Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Activitystreams.NAMESPACE, Activitystreams.OBJECT_ELEMENT));
				write(object, element);
			}
		}
		if (entry.hasVerbs()) {
			for (ActivityVerb verb : entry.getVerbs()) {
				Element element = (Element) target.appendChild(target.getOwnerDocument().createElementNS(Activitystreams.NAMESPACE, Activitystreams.VERB_ELEMENT));
				write(verb, element);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#write(org.onesocialweb.model.activity.ActivityVerb, org.w3c.dom.Element)
	 */
	public void write (ActivityVerb verb, Element target) {
		setTextContent(target, verb.getValue());
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#write(org.onesocialweb.model.activity.ActivityActor, org.w3c.dom.Element)
	 */
	public void write(ActivityActor actor, Element target) {
		atomDomWriter.write(actor, target);
	}
	
	/* (non-Javadoc)
	 * @see org.onesocialweb.model.activity.ActivityDomWriter#write(org.onesocialweb.model.activity.ActivityObject, org.w3c.dom.Element)
	 */
	public void write (ActivityObject object, Element target) {
		atomDomWriter.write(object, target);
		appendTextNode(target, Activitystreams.NAMESPACE, Activitystreams.OBJECT_TYPE_ELEMENT, object.getType());
	}
	
	protected abstract AclDomWriter getAclDomWriter();
	
	protected abstract AtomDomWriter getAtomDomWriter();
		
}
