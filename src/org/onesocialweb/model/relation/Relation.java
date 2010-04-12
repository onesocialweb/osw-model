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

package org.onesocialweb.model.relation;

import java.util.Date;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public interface Relation {

	public class Nature {
		
		public static String COLLEAGUE = "http://onesocialweb.org/spec/1.0/relation/nature/colleague";
		
		public static String FOLLOWING = "http://onesocialweb.org/spec/1.0/relation/nature/following";
		
		public static String FRIEND = "http://onesocialweb.org/spec/1.0/relation/nature/friend";
		
	}
	
	public class Status {
		
		public static String CONFIRMED = "http://onesocialweb.org/spec/1.0/relation/status/confirmed";
		
		public static String DENIED = "http://onesocialweb.org/spec/1.0/relation/status/denied";
		
		public static String PENDING = "http://onesocialweb.org/spec/1.0/relation/status/pending";
		
		public static String REQUEST = "http://onesocialweb.org/spec/1.0/relation/status/request";
		
	}
	
	public void addAclRule(AclRule rule);
	
	public List<AclRule> getAclRules(); 
	
	public String getComment();
	
	public String getFrom();
	
	public String getId();
	
	public String getMessage();
	
	public String getNature();
	
	public Date getPublished();
	
	public String getStatus();
	
	public String getTo();
	
	public boolean hasAclRules();
	
	public boolean hasComment();
	
	public boolean hasFrom();
	
	public boolean hasId();
	
	public boolean hasMessage();
	
	public boolean hasNature();
	
	public boolean hasPublished();
	
	public boolean hasStatus();
	
	public boolean hasTo();
	
	public void removeAclRule(AclRule rule);
	
	public void setAclRules(List<AclRule> rules);
	
	public void setComment(String comment);
	
	public void setFrom(String from);
	
	public void setId(String id);
	
	public void setMessage(String message);
	
	public void setNature(String nature);
	
	public void setPublished(Date published);

	public void setStatus(String status);
	
	public void setTo(String to);

}
