package org.onesocialweb.model.vcard4;

import java.util.TimeZone;

public abstract class TimeZoneField extends Field
{

	public enum Type {TEXT, URI }
	public static final String NAME = "tz";
		
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getTimeZone();
	}
	
	@Override
	public boolean isValid() {
		if ((getType()==null) || (getValue()==null))
			return false;
		else 
			return true;
	}
	
	public abstract Type getType();
	
	public abstract void setTimeZone(String value, Type type);
	
	public abstract String getTimeZone();
	
	public abstract TimeZone getJavaTimeZone();
		
	public abstract void setJavaTimeZone(TimeZone tz);
	
	
}
