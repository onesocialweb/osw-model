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
package org.onesocialweb.xml.xpp;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.model.activity.ActivityFactory;
import org.onesocialweb.model.activity.DefaultActivityFactory;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.DefaultAtomFactory;
import org.onesocialweb.xml.xpp.imp.DefaultXppActivityReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XppActivityReaderTest {

	@Test
	public void testParse() {

        ActivityEntry entry = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getClass().getClassLoader().getResourceAsStream("activity-entry.xml"), "UTF-8");
            CustomXppActivityReader reader = new CustomXppActivityReader();
            xpp.next();
            entry = reader.parse(xpp);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(entry);
	}
	
	private class CustomXppActivityReader extends DefaultXppActivityReader {

		@Override
		protected ActivityFactory getActivityFactory() {
			return new DefaultActivityFactory();
		}

		@Override
		protected AtomFactory getAtomFactory() {
			return new DefaultAtomFactory();
		}
		
	}

}
