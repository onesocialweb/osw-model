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

public abstract class DefaultAtomCommon implements AtomCommon {

	private String xmlBase;
	
	private String xmlLang;

	@Override
	public String getXmlBase() {
		return xmlBase;
	}

	@Override
	public String getXmlLang() {
		return xmlLang;
	}

	@Override
	public void setXmlBase(String xmlBase) {
		this.xmlBase = xmlBase;
	}

	// TODO Add validation to conform to Atom spec
	@Override
	public void setXmlLang(String xmlLang) {
		this.xmlLang = xmlLang;
	}
	
}
