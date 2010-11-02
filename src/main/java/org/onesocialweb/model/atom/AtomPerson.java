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
package org.onesocialweb.model.atom;

public interface AtomPerson extends AtomCommon {
	
	public String getEmail();

	public String getName();

	public String getUri();

	public void setEmail(String email);

	public void setName(String name);

	public void setUri(String uri);
	
	public boolean hasEmail();
	
	public boolean hasName();
	
	public boolean hasUri();

}