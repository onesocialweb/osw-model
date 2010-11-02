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

public interface AtomLink extends AtomCommon {

	public String getHref();

	public String getHreflang();

	public String getLength();

	public String getRel();

	public String getTitle();

	public String getType();
	
	public int getCount();
	
	public void setCount(int count);

	public void setHref(final String href);

	public void setHreflang(final String hreflang);

	public void setLength(final String length);

	public void setRel(final String rel);

	public void setTitle(final String title);

	public void setType(final String type);
	
	public boolean hasHref();
	
	public boolean hasHreflang();
	
	public boolean hasLength();
	
	public boolean hasRel();
	
	public boolean hasTitle();
	
	public boolean hasType();
	
	public boolean hasCount();

}