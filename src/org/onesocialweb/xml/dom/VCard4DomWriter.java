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
import org.onesocialweb.model.vcard4.BirthdayField;
import org.onesocialweb.model.vcard4.Field;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
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
		if (bday.getType().equals(BirthdayField.Type.Time))
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
	
	public void write(PhotoField photo, Element target) {
		
		appendTextNode(target, NS_VCARD4, "uri", photo.getValue());
	}


	protected abstract AclDomWriter getAclDomWriter();
}
