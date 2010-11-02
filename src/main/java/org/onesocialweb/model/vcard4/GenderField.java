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

public abstract class GenderField extends Field {
	
	public static final String NAME = "gender";
	
	public enum Type {
		NOTKNOWN, MALE, FEMALE, NOTAPPLICABLE
	};

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		switch (getGender()) {
		case NOTKNOWN:
			return "not known";
		case MALE:
			return "male";
		case FEMALE:
			return "female";
		case NOTAPPLICABLE:
			return "not applicable";
		default:
			return "not known";
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		if (getGender()==null)
			return false;
		return true;
	}

	public abstract Type getGender();

	public abstract void setGender(Type type);

}
