package org.onesocialweb.model.vcard4;

@SuppressWarnings("serial")
public abstract class XFeedField extends Field {

public static final String NAME = "x-feed";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getFeed();
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	public abstract String getFeed();
	
	public abstract void setFeed(String feed);
}
