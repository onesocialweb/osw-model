package org.onesocialweb.model.vcard4;

public abstract class TelField extends Field 
{

	public enum Type { WORK, HOME, TEXT, VOICE,
        FAX, CELL, VIDEO, PAGER };
	
	public static final String NAME = "tel";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getNumber();
	}
	
	@Override
	public boolean isValid() {
		if (getValue()!=null) 
				return true;
		else return false;
			
	}
	
	public abstract String getNumber();
	
	public abstract void setNumber(String tel, Type type);
	
	public abstract Type getType();
}
