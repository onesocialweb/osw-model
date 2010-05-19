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

import org.onesocialweb.model.atom.AtomEntry;

public interface ActivityObject extends AtomEntry {

	public static String STATUS_UPDATE = "http://onesocialweb.org/spec/1.0/object/status";
	public static String COMMENT= "http://onesocialweb.org/spec/1.0/object/comment";
	public static String PICTURE = "http://onesocialweb.org/spec/1.0/object/picture";
	public static String VIDEO = "http://onesocialweb.org/spec/1.0/object/video";
	public static String LINK = "http://onesocialweb.org/spec/1.0/object/link";
	
	public String getType();

	public void setType(String type);

}