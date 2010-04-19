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

import java.util.ArrayList;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.vcard4.BirthdayField;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.VCard4Factory;
import org.onesocialweb.model.vcard4.exception.CardinalityException;
import org.onesocialweb.model.vcard4.exception.UnsupportedFieldException;
import org.onesocialweb.xml.namespace.VCard4;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class VCard4DomReader
{

	private final VCard4Factory factory;
	private final AclDomReader aclDomReader;
	
	private final static String NS_VCARD4 = "urn:ietf:params:xml:ns:vcard-4.0";

	private final static String NS_ONESOCIALWEB = "http://onesocialweb.org/spec/1.0/";

	
	public VCard4DomReader()
	{
		this.factory=getProfileFactory();
		this.aclDomReader=getAclDomReader();
	}
	
	
	public Profile readProfile(Element element)
	{
		final Profile profile =factory.profile();
		
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final Element e = (Element) nodes.item(i);
				final String name = e.getNodeName();
				
				try {
					if (name.equals(VCard4.BDAY_ELEMENT)) {
						profile.addField(readBirthday(e));
					} else if (name.equals(VCard4.FN_ELEMENT)) {
						profile.addField(readFullName(e));
					} else if (name.equals(VCard4.GENDER_ELEMENT)) {
						profile.addField(readGender(e));
					} else if (name.equals(VCard4.NOTE_ELEMENT)) {
						profile.addField(readNote(e));
					} else if (name.equals(VCard4.PHOTO_ELEMENT)) {
						profile.addField(readPhoto(e));				
					}
				} catch (CardinalityException ex) {
					
				} catch (UnsupportedFieldException ex) {
					
				}
			}
		}

		return profile;
	}
	
	protected BirthdayField readBirthday(Element element) {
		BirthdayField field=factory.birthday();
		
		String childElementName=element.getFirstChild().getNodeName();
		if ((childElementName!=null) && (childElementName.length()!=0))
			childElementName=childElementName.trim();
		else return field;
		
		String elementText=DomHelper.getElementText(element, childElementName,NS_VCARD4);
		if (!validDateOrTime(childElementName,elementText))
			return field;
		
		if (childElementName.equalsIgnoreCase("date"))
			
			field.setBirthday(elementText, BirthdayField.Type.Date);
		else if (childElementName.equalsIgnoreCase("time"))
			field.setBirthday(elementText, BirthdayField.Type.Time);
		else if (childElementName.equalsIgnoreCase("date-time"))
			field.setBirthday(elementText, BirthdayField.Type.DateTime);
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected FullNameField readFullName(Element element) {
		FullNameField field=factory.fullname();		
		field.setFullName(DomHelper.getElementText(element, "text",NS_VCARD4));		
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected GenderField readGender(Element element) {
		GenderField field=factory.gender();
		
		String sexValue=element.getTextContent().trim();
		int sexInt=Integer.parseInt(sexValue);
	
		switch (sexInt)
		{
		case 0:
			field.setGender(GenderField.Type.NOTKNOWN);
			break;
		case 1:
			field.setGender(GenderField.Type.MALE);
			break;
		case 2:
			field.setGender(GenderField.Type.FEMALE);
			break;
		case 9:
			field.setGender(GenderField.Type.NOTAPPLICABLE);
			break;		
		}
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected NoteField readNote(Element element) {
		NoteField field=factory.note();		
		field.setNote(DomHelper.getElementText(element, "text",NS_VCARD4));
		
		field.setAclRules(readRules(element));		
		return field;
	}
	
	protected PhotoField readPhoto(Element element) {
		PhotoField field=factory.photo();		
		field.setUri(DomHelper.getElementText(element, "uri",NS_VCARD4));		
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected List<AclRule> readRules(Element element) {
		final List<AclRule> rules = new ArrayList<AclRule>();
		NodeList nodes = element.getElementsByTagNameNS(NS_ONESOCIALWEB, "acl-rule");
		for (int i = 0; i < nodes.getLength(); i++) {
			rules.add(aclDomReader.readRule((Element) nodes.item(i)));
		}
		return rules;
	}
	
	private boolean validDateOrTime(String type,String dateOrTime)
	{
		if ((type.equals("date")) && (dateOrTime.matches("\\d{8}|\\d{4}-\\d\\d|--\\d\\d(\\d\\d)?|---\\d\\d")))
		return true;
		else if ((type.equals("time")) && (dateOrTime.matches("(\\d\\d(\\d\\d(\\d\\d)?)?|-\\d\\d(\\d\\d?)|--\\d\\d)(Z|[+\\-]\\d\\d(\\d\\d)?)?")))
			return true;
		else if  ((type.equals("date-time") && (dateOrTime.matches("(\\d{8}|--\\d{4}|---\\d\\d)T\\d\\d(\\d\\d(\\d\\d)?)?(Z|[+\\-]\\d\\d(\\d\\d)?)?"))))
			return true;
		else 
			return false;
	}
	
	protected abstract VCard4Factory getProfileFactory();

	protected abstract AclDomReader getAclDomReader();
}
