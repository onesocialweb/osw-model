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

public class DefaultVCard4Factory extends VCard4Factory {

	@Override
	public BirthdayField birthday() {
		return new DefaultBirthdayField();
	}

	@Override
	public FullNameField fullname() {
		return new DefaultFullNameField();
	}

	@Override
	public GenderField gender() {
		return new DefaultGenderField();
	}

	@Override
	public NoteField note() {
		return new DefaultNoteField();
	}

	@Override
	public PhotoField photo() {
		return new DefaultPhotoField();
	}

	@Override
	public Profile profile() {
		return new DefaultProfile();
	}
	
	@Override
	public EmailField email(){
		return new DefaultEmailField();
	}
	
	@Override
	public NameField name(){
		return new DefaultNameField();
	}
	
	@Override
	public TelField tel(){
		return new DefaultTelField();
	}
	
	@Override
	public TimeZoneField timeZone(){
		return new DefaultTimeZoneField();
	}
	
	public URLField url(){
		return new DefaultURLField();
	}

}
