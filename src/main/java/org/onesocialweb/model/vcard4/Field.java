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

import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public abstract class Field {
	
	public abstract String getName();

	public abstract List<AclRule> getAclRules();
	
	public abstract void setAclRules(List<AclRule> rules);

	public abstract void addAclRule(AclRule rule);

	public abstract void removeAclRule(AclRule rule);
	
	public abstract boolean hasAclRules();
	
	/**
	 * @return a human readable representation of the field content
	 */
	public abstract String getValue();
	
	/**
	 * @return is the field valid?
	 */
	public abstract boolean isValid();
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getName() + ": " + getValue());
		if (hasAclRules()) {
			buffer.append(" [");
			for (AclRule rule : getAclRules()) {
				buffer.append(rule.toString());
			}
			buffer.append("]");
		}
		return buffer.toString();
	}
}
