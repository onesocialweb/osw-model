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

import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.vcard4.BirthdayField;
import org.onesocialweb.model.vcard4.Field;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.onesocialweb.xml.namespace.VCard4;

public class VCard4XmlWriter extends XmlWriter {

	public String toXml(Profile profile) {
		toXml(profile, new StringBuffer());
		return buffer.toString();
	}

	public void toXml(Profile profile, StringBuffer buffer) {
		// Prepare for writing
		this.buffer = buffer;

		// Open the entry tag
		startTag(VCard4.VCARD_ELEMENT);
		attribute("xmlns", VCard4.NAMESPACE);
		attribute("xmlns:osw", Onesocialweb.NAMESPACE);
		endOpen();

			

		// Dump the fields
		for (Field field : profile.getFields()) {
			if (field instanceof BirthdayField) {
				dump((BirthdayField) field);
			} else if (field instanceof FullNameField) {
				dump((FullNameField) field);
			} else if (field instanceof GenderField) {
				dump((GenderField) field);
			} else if (field instanceof NoteField) {
				dump((NoteField) field);
			} else if (field instanceof PhotoField) {
				dump((PhotoField) field);
			} 
		}
		// Close
		closeTag(VCard4.VCARD_ELEMENT);		
	}

	private void dump(BirthdayField field) {
		openTag(VCard4.BDAY_ELEMENT);
		
		BirthdayField.Type type= field.getType();
		if (type.equals(BirthdayField.Type.Date))
			text(VCard4.DATE_ELEMENT, field.getValue());
		if (type.equals(BirthdayField.Type.DateTime))
			text(VCard4.DATETIME_ELEMENT, field.getValue());
		if (type.equals(BirthdayField.Type.Time))
			text(VCard4.TIME_ELEMENT, field.getValue());
		
		dumpAclRules(field);
		closeTag(VCard4.BDAY_ELEMENT);
	}

	private void dump(FullNameField field) {
		openTag(VCard4.FN_ELEMENT);
		text(VCard4.TEXT_ELEMENT, field.getFullName());		
		dumpAclRules(field);
		closeTag(VCard4.FN_ELEMENT);
	}

	private void dump(GenderField field) {
		
		GenderField.Type type= field.getGender();				
		if (type.equals(GenderField.Type.NOTKNOWN))
			text(VCard4.GENDER_ELEMENT, "0");
		if (type.equals(GenderField.Type.MALE))
			text(VCard4.GENDER_ELEMENT, "1");
		if (type.equals(GenderField.Type.FEMALE))
			text(VCard4.GENDER_ELEMENT, "2");
		if (type.equals(GenderField.Type.NOTAPPLICABLE))
			text(VCard4.GENDER_ELEMENT, "9");				
		
	}

	private void dump(NoteField field) {
		openTag(VCard4.NOTE_ELEMENT);
		text(VCard4.TEXT_ELEMENT, field.getNote());		
		dumpAclRules(field);
		closeTag(VCard4.NOTE_ELEMENT);
	}
	
	private void dump(PhotoField field) {
		openTag(VCard4.PHOTO_ELEMENT);
		text(VCard4.URI_ELEMENT, field.getValue());
		
		dumpAclRules(field);
		closeTag(VCard4.PHOTO_ELEMENT);
	}
	

	private void dumpAclRules(Field field) {
		if (field.hasAclRules()) {
			for (AclRule rule : field.getAclRules()) {
				openTag("osw:" + Onesocialweb.ACL_RULE_ELEMENT);
				if (rule.hasActions()) {
					for (AclAction action : rule.getActions()) {
						startTag("osw:" + Onesocialweb.ACL_ACTION_ELEMENT);
						attribute(Onesocialweb.PERMISSION_ATTRIBUTE, action.getPermission());
						endOpen();
						if (action.hasName())
							buffer.append(action.getName());
						closeTag("osw:" + Onesocialweb.ACL_ACTION_ELEMENT);
					}
				}
				if (rule.hasSubjects()) {
					for (AclSubject subject : rule.getSubjects()) {
						startTag("osw:" + Onesocialweb.ACL_SUBJECT_ELEMENT);
						attribute(Onesocialweb.TYPE_ATTRIBUTE, subject.getType());
						endOpen();
						if (subject.hasName())
							buffer.append(subject.getName());
						closeTag("osw:" + Onesocialweb.ACL_SUBJECT_ELEMENT);
					}
				}
				closeTag("osw:" + Onesocialweb.ACL_RULE_ELEMENT);
			}
		}
	}
}
