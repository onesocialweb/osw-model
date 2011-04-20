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
import org.onesocialweb.model.vcard4.EmailField;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.GenderField;
import org.onesocialweb.model.vcard4.NameField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.TelField;
import org.onesocialweb.model.vcard4.TimeZoneField;
import org.onesocialweb.model.vcard4.URLField;
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
					}else if (name.equals(VCard4.EMAIL_ELEMENT)) {
						profile.addField(readEmail(e));				
					}else if (name.equals(VCard4.N_ELEMENT)) {
						profile.addField(readName(e));				
					}else if (name.equals(VCard4.TEL_ELEMENT)) {
						profile.addField(readTel(e));				
					}else if (name.equals(VCard4.TZ_ELEMENT)) {
						profile.addField(readTimeZone(e));				
					}else if (name.equals(VCard4.URL_ELEMENT)) {
						profile.addField(readURL(e));				
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
		Element e=null;
		String childElementName="";
		BirthdayField.Type type=null;

		NodeList childElements=element.getElementsByTagName("date-time");
		if (childElements!=null)				
				type=BirthdayField.Type.DateTime;			
		else{
			childElements=element.getElementsByTagName("date");
			if (childElements!=null)
				type=BirthdayField.Type.Date;			
			else {
				childElements=element.getElementsByTagName("time");
				if (childElements!=null)
					type=BirthdayField.Type.Time;				
				else return field;				
			}
		}
		

		if (childElements!=null){
			e=(Element)childElements.item(0);
			if (e!=null)
				childElementName=e.getNodeName();
			else return field;
		}

		else return field;
					
		
		String elementText=DomHelper.getElementText(element, childElementName,NS_VCARD4);

		if (!validDateOrTime(childElementName,elementText))
			return field;
		
		field.setBirthday(elementText, type);
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected FullNameField readFullName(Element element) {
		FullNameField field=factory.fullname();		
		field.setFullName(DomHelper.getElementText(element, VCard4.TEXT_ELEMENT,NS_VCARD4));		
		
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
		field.setNote(DomHelper.getElementText(element, VCard4.TEXT_ELEMENT,NS_VCARD4));
		
		field.setAclRules(readRules(element));		
		return field;
	}
	
	protected PhotoField readPhoto(Element element) {
		PhotoField field=factory.photo();		
		field.setUri(DomHelper.getElementText(element, VCard4.URI_ELEMENT,NS_VCARD4));		
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	
	protected EmailField readEmail(Element element) {
		EmailField field=factory.email();
		String emailTxt=DomHelper.getElementText(element, VCard4.URI_ELEMENT,NS_VCARD4);
		Element parametersElem= (Element) element.getElementsByTagNameNS(NS_VCARD4, "parameters").item(0);
		String typeTxt=	DomHelper.getElementText(parametersElem, VCard4.TYPE_ELEMENT,NS_VCARD4);
		
		if ((emailTxt==null) || (!validEmail(emailTxt)) || (typeTxt==null))
			return field;
		
		if (typeTxt.equalsIgnoreCase("work"))		
			field.setEmail(emailTxt, EmailField.Type.Work);
		else if (typeTxt.equalsIgnoreCase("home"))		
			field.setEmail(emailTxt, EmailField.Type.Home);
		
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected NameField readName(Element element) {
		NameField field=factory.name();
		
		Element childElement=DomHelper.getElement(element, "given", NS_VCARD4);
		field.setGiven(DomHelper.getElementText(childElement, VCard4.TEXT_ELEMENT,NS_VCARD4));
		
		childElement=DomHelper.getElement(element,"prefix", NS_VCARD4);
		field.setPrefix(DomHelper.getElementText(childElement, VCard4.TEXT_ELEMENT,NS_VCARD4));
		
		childElement=DomHelper.getElement(element,"suffix", NS_VCARD4);
		field.setSuffix(DomHelper.getElementText(childElement, VCard4.TEXT_ELEMENT,NS_VCARD4));
		
		childElement=DomHelper.getElement(element,"surname", NS_VCARD4);
		field.setSurname(DomHelper.getElementText(childElement, VCard4.TEXT_ELEMENT,NS_VCARD4));
				
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected TelField readTel(Element element) {
		TelField field=factory.tel();
		String telTxt=DomHelper.getElementText(element, VCard4.URI_ELEMENT,NS_VCARD4);
		Element parametersElem= (Element) element.getElementsByTagNameNS(NS_VCARD4, "parameters").item(0);
		String telType=DomHelper.getElementText(parametersElem, VCard4.TYPE_ELEMENT,NS_VCARD4);

		if ((telTxt!=null) &&  (telType!=null))		{
			if (!telTxt.contains("tel:"))
				telTxt="tel:" + telTxt;			
		}
		else return field;
		if (!validTel(telTxt)) 
			return field;
		
		if (telType.equalsIgnoreCase("work"))
			field.setNumber(telTxt, TelField.Type.WORK);
		else if (telType.equalsIgnoreCase("home"))
			field.setNumber(telTxt, TelField.Type.HOME);
		else if (telType.equalsIgnoreCase("text"))
			field.setNumber(telTxt, TelField.Type.TEXT);
		else if (telType.equalsIgnoreCase("voice"))
			field.setNumber(telTxt, TelField.Type.VOICE);
		else if (telType.equalsIgnoreCase("fax"))
			field.setNumber(telTxt, TelField.Type.FAX);
		else if (telType.equalsIgnoreCase("cell"))
			field.setNumber(telTxt, TelField.Type.CELL);
		else if (telType.equalsIgnoreCase("video"))
			field.setNumber(telTxt, TelField.Type.VIDEO);
		else if (telType.equalsIgnoreCase("pager"))	
			field.setNumber(telTxt, TelField.Type.PAGER);
		
		field.setAclRules(readRules(element));
	
		return field;
	}
	
	protected TimeZoneField readTimeZone(Element element) {
		TimeZoneField field=factory.timeZone();
		TimeZoneField.Type type=null;
		
		String tzTxt=DomHelper.getElementText(element, VCard4.URI_ELEMENT,NS_VCARD4);
		
		if ((tzTxt!=null) && (tzTxt.length()!=0))
			type=TimeZoneField.Type.URI;
		else{
			tzTxt=DomHelper.getElementText(element, VCard4.TEXT_ELEMENT,NS_VCARD4);
			if ((tzTxt==null) || (tzTxt.length()==0))
				return field;
			else 
				type=TimeZoneField.Type.TEXT;
		}
		
		
		field.setTimeZone(tzTxt, type);
		field.setAclRules(readRules(element));
		return field;
	}
	
	protected URLField readURL(Element element) {
		URLField field=factory.url();
		field.setURL(DomHelper.getElementText(element, VCard4.URI_ELEMENT,NS_VCARD4));
		
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
	
	private boolean validEmail(String email)
	{
		if ((email.length()!=0) && (email.matches(".+@.+\\.[a-z]+")) )
			return true;
		else return false;
	}
	
	private boolean validTel(String tel)
	{
		if ((tel.length()!=0) && (tel.matches("^tel:((?:\\+[\\d().-]*\\d[\\d().-]*|[0-9A-F*#().-]*[0-9A-F*#][0-9A-F*#().-]*(?:;[a-z\\d-]+(?:=(?:[a-z\\d\\[\\]\\/:&+$_!~*'().-]|%[\\dA-F]{2})+)?)*;phone-context=(?:\\+[\\d().-]*\\d[\\d().-]*|(?:[a-z0-9]\\.|[a-z0-9][a-z0-9-]*[a-z0-9]\\.)*(?:[a-z]|[a-z][a-z0-9-]*[a-z0-9])))(?:;[a-z\\d-]+(?:=(?:[a-z\\d\\[\\]\\/:&+$_!~*'().-]|%[\\dA-F]{2})+)?)*(?:,(?:\\+[\\d().-]*\\d[\\d().-]*|[0-9A-F*#().-]*[0-9A-F*#][0-9A-F*#().-]*(?:;[a-z\\d-]+(?:=(?:[a-z\\d\\[\\]\\/:&+$_!~*'().-]|%[\\dA-F]{2})+)?)*;phone-context=\\+[\\d().-]*\\d[\\d().-]*)(?:;[a-z\\d-]+(?:=(?:[a-z\\d\\[\\]\\/:&+$_!~*'().-]|%[\\dA-F]{2})+)?)*)*)$")))
			return true;
		else return false;
	}
	
	
	private boolean validDateOrTime(String type,String dateOrTime)
	{
	
		return true;
	
	}
	
	protected abstract VCard4Factory getProfileFactory();

	protected abstract AclDomReader getAclDomReader();
}
