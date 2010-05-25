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

package org.onesocialweb.model.vcard4;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.onesocialweb.model.vcard4.exception.CardinalityException;
import org.onesocialweb.model.vcard4.exception.UnsupportedFieldException;

public abstract class Profile {
	
	public abstract String getUserId();

	public abstract void setUserId(String userId);
	
	public abstract boolean hasField(String name);
	
	public abstract Field getField(String name);
	
	public abstract List<Field> getFields();
	
	public abstract List<Field> getFields(String name);
	
	public abstract void addField(Field field) throws UnsupportedFieldException, CardinalityException;
	
	public abstract void removeField(Field field);
	
	public abstract void removeAll();
	
	public void removeAll(String name) {
		for (Field field : getFields(name)) {
			removeField(field);
		}
	}
	
	public String getFullName() {
		if (hasField(FullNameField.NAME)) {
			return ((FullNameField) getField(FullNameField.NAME)).getFullName();
		} else {
			return null;
		}
	}
	
	public GenderField.Type getGender() {
		if (hasField(GenderField.NAME)) {
			return ((GenderField) getField(GenderField.NAME)).getGender();
		} else {
			return GenderField.Type.NOTKNOWN;
		}
	}
	
	public String getNote() {
		if (hasField(NoteField.NAME)) {
			return ((NoteField) getField(NoteField.NAME)).getNote();
		} else {
			return null;
		}
	}
	
	public String getPhotoUri() {
		if (hasField(PhotoField.NAME)) {
			return ((PhotoField) getField(PhotoField.NAME)).getUri();
		} else {
			return null;
		}
	}
	
	public Date getBirthday() {
		if (hasField(BirthdayField.NAME)) {
			return ((BirthdayField) getField(BirthdayField.NAME)).getDate();
		} else {
			return null;
		}
	}
	
	public String getEmail(){
		if (hasField(EmailField.NAME)){		
			return ((EmailField) getField(EmailField.NAME)).getEmail();
		}
		else return null;
	}
	
	public String getName(){
		if (hasField(NameField.NAME)){		
			return ((NameField) getField(NameField.NAME)).getValue();
		}
		else return null;
	}
	
	public String getTel(){
		if (hasField(TelField.NAME)){		
			return ((TelField) getField(TelField.NAME)).getNumber();
		}
		else return null;
	}
	
	public String getTimeZone(){
		if (hasField(TimeZoneField.NAME)){		
			return ((TimeZoneField) getField(TimeZoneField.NAME)).getTimeZone();
		}
		else return null;
	}
	
	public TimeZone getJavaTimeZone(){
		if (hasField(TimeZoneField.NAME)){		
			return ((TimeZoneField) getField(TimeZoneField.NAME)).getJavaTimeZone();
		}
		else return null;
	}
	
	public String getUrl(){
		if (hasField(URLField.NAME)){		
			return ((URLField) getField(URLField.NAME)).getURL();
		}
		else return null;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Profile (" + getUserId() + "): \n");
		for (Field field : getFields()) {
			buffer.append(field.toString() + "\n");
		}
		return buffer.toString();
	}
}
