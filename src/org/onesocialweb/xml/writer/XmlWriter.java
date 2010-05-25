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
package org.onesocialweb.xml.writer;

import org.apache.commons.lang.StringEscapeUtils;

public class XmlWriter {

	protected StringBuffer buffer;
	
	protected void openTag(String tag) {
		startTag(tag);
		endOpen();
	}
	
	protected void startTag(String tag) {
		buffer.append("<" + tag);
	}
	
	protected void endOpen() {
		buffer.append(">");
	}
	
	protected void endClosed() {
		buffer.append("/>");
	}
	
	protected void closeTag(String tag) {
		buffer.append("</" + tag + ">");
	}
	
	protected void attribute(String key, String value) {
		buffer.append(" " + key + "=\"" + xmlEncode(value) + "\"");
	}
	
	protected void text(String tag, String value) {
		startTag(tag);
		endOpen();
		buffer.append(xmlEncode(value));
		closeTag(tag);
	}
	
	protected String xmlEncode(String originalText) {
		return StringEscapeUtils.escapeXml(originalText);
	}
	
}
