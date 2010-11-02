package org.onesocialweb.model.vcard4;

public abstract class URLField extends Field
{

public static final String NAME = "url";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getURL();
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	public abstract String getURL();
	
	public abstract void setURL(String url);
}
