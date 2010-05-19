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

import org.onesocialweb.model.acl.AclFactory;
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
import org.onesocialweb.xml.namespace.Onesocialweb;
import org.onesocialweb.xml.namespace.VCard4;
import org.onesocialweb.xml.xpp.imp.DefaultXppAclReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class XppVCard4Reader implements XppReader<Profile> {

	private final VCard4Factory profileFactory;
	
	private final DefaultXppAclReader aclReader = new DefaultXppAclReader();
	
	
	public XppVCard4Reader() {
		this.profileFactory = getProfileFactory();
			
	}

	@Override
	public Profile parse(XmlPullParser parser) throws XmlPullParserException, IOException {
						
		
		// Verify that we are on the right token
		if (!(parser.getNamespace().equals(VCard4.NAMESPACE) 
				&& parser.getName().equals(VCard4.VCARD_ELEMENT))) {
			throw new XmlPullParserException("Unexpected token " + parser);
		}

		//Proceed with parsing
		final Profile profile = profileFactory.profile();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					try{
						if (name.equals(VCard4.FN_ELEMENT)) {
							profile.addField(parseFullName(parser));
						} else if (name.equals(VCard4.BDAY_ELEMENT)) {
							profile.addField(parseBirthday(parser));
						} else if (name.equals(VCard4.GENDER_ELEMENT)) {
							profile.addField(parseGender(parser));
						} else if (name.equals(VCard4.NOTE_ELEMENT)) {
							profile.addField(parseNote(parser));
						} else if (name.equals(VCard4.PHOTO_ELEMENT)) {
							profile.addField(parsePhoto(parser));
						} else if (name.equals(VCard4.EMAIL_ELEMENT)) {
							profile.addField(parseEmail(parser));
						} else if (name.equals(VCard4.N_ELEMENT)) {
							profile.addField(parseName(parser));
						} else if (name.equals(VCard4.TEL_ELEMENT)) {
							profile.addField(parseTel(parser));
						} else if (name.equals(VCard4.URL_ELEMENT)) {
							profile.addField(parseUrl(parser));
						}  else if (name.equals(VCard4.TZ_ELEMENT)) {
						profile.addField(parseTimeZone(parser));
					} 
					}catch (CardinalityException ex) {

					} catch (UnsupportedFieldException ex) {

					}

				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.VCARD_ELEMENT)) {
					done = true;
				}
			}
		}
		return profile;
	}
	
	protected FullNameField parseFullName(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final FullNameField field = profileFactory.fullname();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.TEXT_ELEMENT)) {
						field.setFullName((parser.nextText().trim()));
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.FN_ELEMENT)) {
					done = true;
				}
			}
		}
		return field;
	}
	
	protected BirthdayField parseBirthday(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final BirthdayField field = profileFactory.birthday();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.DATE_ELEMENT)) {
						field.setBirthday(parser.nextText().trim(), BirthdayField.Type.Date);
					} 
					if (name.equals(VCard4.TIME_ELEMENT)) {
						field.setBirthday(parser.nextText().trim(), BirthdayField.Type.Time);
					}
					if (name.equals(VCard4.DATETIME_ELEMENT)) {
						field.setBirthday(parser.nextText().trim(), BirthdayField.Type.DateTime);
					}
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.BDAY_ELEMENT)) {
					done = true;
				}
			}
		}
		return field;
	}
	
	protected GenderField parseGender(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final GenderField field = profileFactory.gender();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String namespace = parser.getNamespace();
			String name = parser.getName();		

			if (eventType == XmlPullParser.TEXT) {

				String genderString=parser.getText().trim();			
				int genderNumber=Integer.parseInt(genderString);
				switch (genderNumber)
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
			}							
			if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.GENDER_ELEMENT)) {
					done = true;
				}
			}
		}
		return field;
	}
	
	protected NoteField parseNote(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final NoteField field = profileFactory.note();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.TEXT_ELEMENT)) {
						field.setNote(parser.nextText().trim());
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.NOTE_ELEMENT)) {
					done = true;
				}
			}
		}
		return field;
	}
	
	protected PhotoField parsePhoto(XmlPullParser parser) throws XmlPullParserException, IOException {	
		final PhotoField field = profileFactory.photo();
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.URI_ELEMENT)) {
						field.setUri(parser.nextText().trim());
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.PHOTO_ELEMENT)) {
					done = true;
				}
			}
		}
		return field;
	}
	
	protected EmailField parseEmail(XmlPullParser parser) throws XmlPullParserException, IOException {
		final EmailField field = profileFactory.email();
		
		String typeStr=null;
		String emailAddrr=null;
		
		boolean done = false;
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.URI_ELEMENT)) {
						emailAddrr=parser.nextText().trim();						
					} 
					else if (name.equals(VCard4.TYPE_ELEMENT)) {
						typeStr=parser.nextText().trim();						
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.EMAIL_ELEMENT)) {
					done = true;
				}
			}
		}
		
		if (typeStr.equalsIgnoreCase("work"))
				field.setEmail(emailAddrr, EmailField.Type.Work);
		else if (typeStr.equalsIgnoreCase("home"))
		field.setEmail(emailAddrr, EmailField.Type.Home);
		
		return field;
	}
	
	protected NameField parseName(XmlPullParser parser) throws XmlPullParserException, IOException {
		final NameField field = profileFactory.name();
		
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals("prefix")) {
						parser.nextTag();
						field.setPrefix(parser.nextText().trim());
					}
					else if (name.equals("given")) {
						parser.nextTag();
						field.setGiven(parser.nextText().trim());
					}
					else if (name.equals("surname")) {
						parser.nextTag();
						field.setSurname(parser.nextText().trim());
					} 
					else if (name.equals("suffix")) {
						parser.nextTag();
						field.setSuffix(parser.nextText().trim());
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.N_ELEMENT)) {
					done = true;
				}
			}
		}
		
		return field;
	}
	
	protected TelField parseTel(XmlPullParser parser) throws XmlPullParserException, IOException {
		final TelField field = profileFactory.tel();
		
		String telStr=null;
		String typeStr=null;
		
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.URI_ELEMENT)) {
						telStr=parser.nextText().trim();
					}
					else if ((name.equals(VCard4.TYPE_ELEMENT))) {
						typeStr=parser.nextText().trim();
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.TEL_ELEMENT)) {
					done = true;
				}
			}
		}
		
		if (typeStr.equals("work"))
				field.setNumber(telStr, TelField.Type.WORK);
		else if (typeStr.equals("home"))
			field.setNumber(telStr, TelField.Type.HOME);
		else if (typeStr.equals("text"))
			field.setNumber(telStr, TelField.Type.TEXT);
		else if (typeStr.equals("voice"))
			field.setNumber(telStr, TelField.Type.VOICE);
		else if (typeStr.equals("fax")) 
			field.setNumber(telStr, TelField.Type.FAX);
		else if (typeStr.equals("cell")) 
			field.setNumber(telStr, TelField.Type.CELL);
		else if (typeStr.equals("video")) 
			field.setNumber(telStr, TelField.Type.VIDEO);
		else if (typeStr.equals("pager"))
			field.setNumber(telStr, TelField.Type.PAGER);
		
		return field;
	}
	
	protected TimeZoneField parseTimeZone(XmlPullParser parser) throws XmlPullParserException, IOException {
		final TimeZoneField field = profileFactory.timeZone();
		
		boolean done = false;

		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.URI_ELEMENT)) {						
						field.setTimeZone(parser.nextText().trim(), TimeZoneField.Type.URI);					
					} 
					else if (name.equals(VCard4.TEXT_ELEMENT)) {						
						field.setTimeZone(parser.nextText().trim(), TimeZoneField.Type.TEXT);					
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.TZ_ELEMENT)) {
					done = true;
				}
			}
		}
		
		return field;
	}
	
	protected URLField parseUrl(XmlPullParser parser) throws XmlPullParserException, IOException {
		final URLField field = profileFactory.url();
		
		boolean done = false;
		
		while (!done) {
			int eventType = parser.next();
			String name = parser.getName();
			String namespace = parser.getNamespace();
			if (eventType == XmlPullParser.START_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)) {
					if (name.equals(VCard4.URI_ELEMENT)) {
						field.setURL(parser.nextText().trim());
					} 
				} else if (namespace.equals(Onesocialweb.NAMESPACE)) {
					if (name.equals(Onesocialweb.ACL_RULE_ELEMENT)) {
						field.addAclRule(aclReader.parse(parser));
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (namespace.equals(VCard4.NAMESPACE)
						&& name.equals(VCard4.URL_ELEMENT)) {
					done = true;
				}
			}
		}
		
		return field;
	}
		
	abstract protected AclFactory getAclFactory();
	
	abstract protected VCard4Factory getProfileFactory();

}

