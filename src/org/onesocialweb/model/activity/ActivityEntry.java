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

import java.util.List;

import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.atom.AtomEntry;

public interface ActivityEntry extends AtomEntry {

	public void addObject(ActivityObject object);
	
	public void addVerb(ActivityVerb verb);
	
	public void addAclRule(AclRule rule);
	
	public List<AclRule> getAclRules();
	
	public ActivityActor getActor();

	public List<ActivityObject> getObjects();

	public List<ActivityVerb> getVerbs();

	
	public boolean hasAclRules();
	
	public boolean hasObjects();
	
	public boolean hasActor();
	
	public boolean hasVerbs();
	
	public void removeObject(ActivityObject object);
	
	public void removeVerb(ActivityVerb verb);
	
	public void removeAclRule(AclRule rule);		

	public void setAclRules(List<AclRule> rules);

	public void setActor(final ActivityActor actor);

	public void setObjects(List<ActivityObject> objects);

	public void setVerbs(final List<ActivityVerb> verbs);

}