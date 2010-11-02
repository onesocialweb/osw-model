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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultProfile extends Profile {

	private List<Field> fields = new ArrayList<Field>();
	
	private String userId;
	
	@Override
	public void addField(Field field) {
		fields.add(field);
	}

	@Override
	public Field getField(String name) {
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		
		return null;
	}

	@Override
	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

	@Override
	public List<Field> getFields(String name) {
		final List<Field> result = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				result.add(field);
			}
		}
		return Collections.unmodifiableList(result);
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public boolean hasField(String name) {
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeAll() {
		fields.clear();
	}

	@Override
	public void removeField(Field field) {
		fields.remove(field);
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
