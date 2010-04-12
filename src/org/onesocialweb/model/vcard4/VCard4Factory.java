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

public abstract class VCard4Factory {
	
	public abstract Profile profile();
	
	public abstract FullNameField fullname();
	
	public abstract GenderField gender();
	
	public abstract NoteField note();
	
	public abstract PhotoField photo();
	
	public abstract BirthdayField birthday();
	
	public FullNameField fullname(String name) {
		final FullNameField field = fullname();
		field.setFullName(name);
		return field;
	}
	
	public GenderField gender(GenderField.Type type) {
		final GenderField field = gender();
		field.setGender(type);
		return field;
	}
	
	public NoteField note(String note) {
		final NoteField field = note();
		field.setNote(note);
		return field;
	}
	
	public PhotoField photo(String uri) {
		final PhotoField field = photo();
		field.setUri(uri);
		return field;
	}
	
	public BirthdayField birthday(Date date) {
		final BirthdayField field = birthday();
		field.setDate(date);
		return field;
	}

}
