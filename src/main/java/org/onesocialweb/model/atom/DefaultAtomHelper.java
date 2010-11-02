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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultAtomHelper {

	public static String generateId() {
		return "urn:uuid:" + UUID.randomUUID().toString();
	}

	public static Date parseDate(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		Pattern pattern = Pattern
				.compile("(\\d{4})(?:-(\\d{2}))?(?:-(\\d{2}))?(?:([Tt])?(?:(\\d{2}))?(?::(\\d{2}))?(?::(\\d{2}))?(?:\\.(\\d{3}))?)?([Zz])?(?:([+-])(\\d{2}):(\\d{2}))?");
		Matcher m = pattern.matcher(date);
		if (m.find()) {
			if (m.group(4) == null)
				throw new IllegalArgumentException("Invalid Date Format");
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			int hoff = 0, moff = 0, doff = -1;
			if (m.group(10) != null) {
				doff = m.group(10).equals("-") ? 1 : -1;
				hoff = doff * (m.group(11) != null ? Integer.parseInt(m.group(11)) : 0);
				moff = doff * (m.group(12) != null ? Integer.parseInt(m.group(12)) : 0);
			}
			c.set(Calendar.YEAR, Integer.parseInt(m.group(1)));
			c.set(Calendar.MONTH, m.group(2) != null ? Integer.parseInt(m.group(2)) - 1 : 0);
			c.set(Calendar.DATE, m.group(3) != null ? Integer.parseInt(m.group(3)) : 1);
			c.set(Calendar.HOUR_OF_DAY, m.group(5) != null ? Integer.parseInt(m.group(5)) + hoff : 0);
			c.set(Calendar.MINUTE, m.group(6) != null ? Integer.parseInt(m.group(6)) + moff : 0);
			c.set(Calendar.SECOND, m.group(7) != null ? Integer.parseInt(m.group(7)) : 0);
			c.set(Calendar.MILLISECOND, m.group(8) != null ? Integer.parseInt(m.group(8)) : 0);
			return c.getTime();
		} else {
			throw new IllegalArgumentException("Invalid Date Format: " + date);
		}
	}

	public static String format(Date date) {

		if (date == null) {
			return new String();
		}

		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.setTime(date);
		sb.append(c.get(Calendar.YEAR));
		sb.append('-');
		int f = c.get(Calendar.MONTH);
		if (f < 9)
			sb.append('0');
		sb.append(f + 1);
		sb.append('-');
		f = c.get(Calendar.DATE);
		if (f < 10)
			sb.append('0');
		sb.append(f);
		sb.append('T');
		f = c.get(Calendar.HOUR_OF_DAY);
		if (f < 10)
			sb.append('0');
		sb.append(f);
		sb.append(':');
		f = c.get(Calendar.MINUTE);
		if (f < 10)
			sb.append('0');
		sb.append(f);
		sb.append(':');
		f = c.get(Calendar.SECOND);
		if (f < 10)
			sb.append('0');
		sb.append(f);
		sb.append('.');
		f = c.get(Calendar.MILLISECOND);
		if (f < 100)
			sb.append('0');
		if (f < 10)
			sb.append('0');
		sb.append(f);
		sb.append('Z');
		return sb.toString();
	}
}
