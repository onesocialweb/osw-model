package org.onesocialweb.model.vcard4;

@SuppressWarnings("serial")
public abstract class SourceField extends Field {

	public static final String NAME = "source";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getSource();
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	public abstract String getSource();
	
	public abstract void setSource(String source);
}
