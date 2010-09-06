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
package org.onesocialweb.model.activity;

import org.onesocialweb.model.atom.DefaultAtomEntry;

public class DefaultActivityObject extends DefaultAtomEntry implements ActivityObject {
	
	private String type;

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof DefaultActivityObject)) return false;
		else return ((DefaultActivityObject)other).getType().equals(getType());
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ActivityObject ");
		if (type != null) {
			buffer.append("type:" + type + " ");
		}
		buffer.append(super.toString());
		buffer.append("]");
		return buffer.toString();
	}
}
