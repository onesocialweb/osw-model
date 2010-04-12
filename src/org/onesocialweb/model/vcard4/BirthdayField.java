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

public abstract class BirthdayField extends Field {

	public enum Type {Date, DateTime, Time};

	public static final String NAME = "bday";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override 
	public String getValue() {
		return getBirthday();
	}
	
	@Override
	public boolean isValid() {
		if (getType()==null)
			return false;
		else 
			return true;
	}
	
	public abstract void setBirthday(String value, Type type);
	
	public abstract String getBirthday();
	
	public abstract Type getType();
	
	public abstract Date getDate();
	
	public abstract void setDate(Date date);

}
