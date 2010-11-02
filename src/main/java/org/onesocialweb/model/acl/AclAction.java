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
package org.onesocialweb.model.acl;

public interface AclAction {

	public static final String ACTION_VIEW = "http://onesocialweb.org/spec/1.0/acl/action/view";
	
	public static final String PERMISSION_GRANT = "http://onesocialweb.org/spec/1.0/acl/permission/grant";
	
	public static final String PERMISSION_DENY = "http://onesocialweb.org/spec/1.0/acl/permission/deny";

	public String getPermission();

	public void setPermission(String permission);
	
	public boolean hasPermission();

	public String getName();

	public void setName(String name);
	
	public boolean hasName();
	
}