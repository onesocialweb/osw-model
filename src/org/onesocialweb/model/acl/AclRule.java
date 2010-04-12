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

import java.util.List;

public interface AclRule {

	public void addAction(AclAction action);
	
	public void addSubject(AclSubject subject);

	public List<AclAction> getActions();
	
	public List<AclAction> getActions(String name, String permission);
	
	public List<AclSubject> getSubjects();

	public List<AclSubject> getSubjects(String type);
	
	public boolean hasAction(AclAction action);
	
	public boolean hasActions();
	
	public boolean hasSubject(AclSubject subject);
	
	public boolean hasSubjects();

	public void removeAction(AclAction action);
	
	public void removeSubject(AclSubject subject);
	
	public void setActions(List<AclAction> actions);
	
	public void setSubjects(List<AclSubject> subjects);

}