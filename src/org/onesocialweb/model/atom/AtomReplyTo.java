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

public interface AtomReplyTo {
	
	public String getRef();
	
	public String getHref();
	
	public String getType();
	
	public String getSource();
	
	public void setRef(String ref);
	
	public void setHref(String href);
	
	public void setType(String type);
	
	public void setSource(String source);
	
	public boolean hasRef();
	
	public boolean hasHref();
	
	public boolean hasType();
	
	public boolean hasSource();

}
