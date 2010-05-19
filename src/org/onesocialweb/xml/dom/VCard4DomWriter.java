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

import static org.onesocialweb.xml.dom.DomHelper.appendParametersNode;
import static org.onesocialweb.xml.dom.DomHelper.appendTextNode;
import static org.onesocialweb.xml.dom.DomHelper.setTextContent;

import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.vcard4.BirthdayField;
import org.onesocialweb.model.vcard4.EmailField;
import org.onesocialweb.model.vcard4.Field;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.NameField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.TelField;
import org.onesocialweb.model.vcard4.TimeZoneField;
import org.onesocialweb.model.vcard4.URLField;
import org.onesocialweb.xml.namespace.VCard4;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class VCard4DomWriter
{

	private final static String NS_VCARD4 = VCard4.NAMESPACE;

	private final AclDomWriter aclDomWriter;

	public VCard4DomWriter() {
		aclDomWriter = getAclDomWriter();
	}


	public Element toElement(Profile profile, Document document) {
		Element root = document.createElementNS(VCard4.NAMESPACE, VCard4.VCARD_ELEMENT);
		document.appendChild(root);
		write(profile, root);
		return root;
	}

	public Element toElement(Profile profile, Element parent) {
		Element root = (Element) parent.appendChild(parent.getOwnerDocument().createElementNS(NS_VCARD4, "vcard"));
		write(profile, root);
		return root;
	}

	public void write(Profile profile, Element target) {

		for (Field field : profile.getFields()) {
			Element e = (Element) target.appendChild(target.getOwnerDocument().createElementNS(NS_VCARD4, field.getName()));
			if (field instanceof BirthdayField) {
				write(((BirthdayField) field), e);
			} else if (field instanceof FullNameField) {
				write(((FullNameField) field), e);
			} else if (field instanceof GenderField) {
				write(((GenderField) field), e); 
			} else if (field instanceof NoteField) {
				write(((NoteField) field), e);
			} else if (field instanceof PhotoField) {
					write(((PhotoField) field), e);
			} else if (field instanceof EmailField) {
					write(((EmailField) field), e);
			} else if (field instanceof NameField) {
					write(((NameField) field), e);
			} else if (field instanceof TelField) {
					write(((TelField) field), e);
			} else if (field instanceof TimeZoneField) {
					write(((TimeZoneField) field), e);
			} else if (field instanceof URLField) {
					write(((URLField) field), e);
			}													 

			if (field.hasAclRules()) {
				for (AclRule aclRule : field.getAclRules()) {
					aclDomWriter.toElement(aclRule, e);
				}
			}
		}

	}

	public void write(BirthdayField bday, Element target) {
		
		if (bday.getType().equals(BirthdayField.Type.Date))
			appendTextNode(target, NS_VCARD4, "date", bday.getValue());
		if (bday.getType().equals(BirthdayField.Type.Time))
			appendTextNode(target, NS_VCARD4, "time", bday.getValue());
		if (bday.getType().equals(BirthdayField.Type.DateTime))
			appendTextNode(target, NS_VCARD4, "date-time", bday.getValue());
	}
	
	public void write(GenderField gender, Element target) {
		
		if (gender.getGender().equals(GenderField.Type.NOTKNOWN))
			setTextContent(target, "0");
		else if (gender.getGender().equals(GenderField.Type.MALE))
			setTextContent(target, "1");
		else if (gender.getGender().equals(GenderField.Type.FEMALE))
			setTextContent(target, "2");
		else if (gender.getGender().equals(GenderField.Type.NOTAPPLICABLE))
			setTextContent(target, "9");
	}

	public void write(FullNameField fn, Element target) {
		
		appendTextNode(target, NS_VCARD4, "text", fn.getFullName());
		
	}
	
	public void write(NoteField note, Element target) {

		appendTextNode(target, NS_VCARD4, "text", note.getNote());
	}
	
	public void write(EmailField email, Element target) {
		
		if (email.getType().equals(EmailField.Type.Work))
			appendParametersNode(target, NS_VCARD4, "type", "work");
		else if (email.getType().equals(EmailField.Type.Home))
			appendParametersNode(target, NS_VCARD4, "type", "home");			
		
		appendTextNode(target, NS_VCARD4, "uri", email.getEmail());
	}
	
	public void write(NameField name, Element target) {
		
		final Document document = target.getOwnerDocument();
		
		final Element nodePrefix = document.createElementNS(NS_VCARD4, "prefix");
		target.appendChild(nodePrefix);
		appendTextNode(nodePrefix, NS_VCARD4, "text", name.getPrefix());
		
		
		final Element nodeGiven = document.createElementNS(NS_VCARD4, "given");
		target.appendChild(nodeGiven);
		appendTextNode(nodeGiven, NS_VCARD4, "text", name.getGiven());
		
		
		final Element nodeSurname = document.createElementNS(NS_VCARD4, "surname");
		target.appendChild(nodeSurname);
		appendTextNode(nodeSurname, NS_VCARD4, "text", name.getSurname());
		
		
		final Element nodeSuffix = document.createElementNS(NS_VCARD4, "suffix");
		target.appendChild(nodeSuffix);		
		appendTextNode(nodeSuffix, NS_VCARD4, "text", name.getSuffix());
				
	}
	
	public void write(TelField tel, Element target) {

		if (tel.getType().equals(TelField.Type.CELL))
			appendParametersNode(target, NS_VCARD4, "type", "cell");
		else if (tel.getType().equals(TelField.Type.FAX))
			appendParametersNode(target, NS_VCARD4, "type", "fax");
		else if (tel.getType().equals(TelField.Type.HOME))
			appendParametersNode(target, NS_VCARD4, "type", "home");
		else if (tel.getType().equals(TelField.Type.PAGER))
			appendParametersNode(target, NS_VCARD4, "type", "pager");
		else if (tel.getType().equals(TelField.Type.TEXT))
			appendParametersNode(target, NS_VCARD4, "type", "text");
		else if (tel.getType().equals(TelField.Type.VIDEO))
			appendParametersNode(target, NS_VCARD4, "type", "video");
		else if (tel.getType().equals(TelField.Type.VOICE))
			appendParametersNode(target, NS_VCARD4, "type", "voice");
		else if (tel.getType().equals(TelField.Type.WORK))
			appendParametersNode(target, NS_VCARD4, "type", "work");
		
		appendTextNode(target, NS_VCARD4, "uri", tel.getNumber());
	}

	public void write(TimeZoneField tz, Element target) {
		if (tz.getType().equals(TimeZoneField.Type.TEXT))
			appendTextNode(target, NS_VCARD4, "uri", tz.getTimeZone());
		else if (tz.getType().equals(TimeZoneField.Type.URI))
			appendTextNode(target, NS_VCARD4, "text", tz.getTimeZone());
	}

	public void write(URLField url, Element target) {

		appendTextNode(target, NS_VCARD4, "uri", url.getURL());
	}

	public void write(PhotoField photo, Element target) {

		appendTextNode(target, NS_VCARD4, "uri", photo.getUri());
	}


	protected abstract AclDomWriter getAclDomWriter();
}
