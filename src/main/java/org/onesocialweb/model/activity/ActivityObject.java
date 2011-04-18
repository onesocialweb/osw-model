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

	public static String STATUS_UPDATE = "http://activitystrea.ms/schema/1.0/status";
	public static String NOTE = "http://activitystrea.ms/schema/1.0/note";
	public static String COMMENT= "http://activitystrea.ms/schema/1.0/comment";
	public static String PICTURE = "http://activitystrea.ms/schema/1.0/photo";
	public static String VIDEO = "http://activitystrea.ms/schema/1.0/video";
	public static String LINK = "http://onesocialweb.org/spec/1.0/object/link";
	public static String PERSON = "http://activitystrea.ms/schema/1.0/person";
	
	public String getType();

	public void setType(String type);

}