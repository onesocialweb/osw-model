package org.onesocialweb.model.vcard4;


public abstract class EmailField  extends Field
{
	public static final String NAME = "email";
	
	public enum Type {Work, Home};
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getEmail();
	}
	
	@Override
	public boolean isValid() {
		if (getEmail()!=null)
			return true;
		else return false;
	}
	
	public abstract String getEmail();
	
	public abstract void setEmail(String email);
	
	public abstract void setEmail(String email, Type type);
	
	public abstract Type getType();
}
