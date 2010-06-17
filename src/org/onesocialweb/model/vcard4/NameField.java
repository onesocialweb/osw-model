package org.onesocialweb.model.vcard4;

public abstract class NameField extends Field
{

	public static final String NAME = "n";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override 
	public String getValue() {
		return this.toString();
	}
	
	@Override
	public boolean isValid() {		
			return true;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();		
		if ((getPrefix()!=null) && (getPrefix().length()!=0))
			buffer.append(getPrefix() + " ");
		if ((getGiven()!=null) && (getGiven().length()!=0))
			buffer.append(getGiven() + " ");
		if ((getSurname()!=null) && (getSurname().length()!=0))
			buffer.append(getSurname() + " ");
		if ((getSuffix()!=null) && (getSuffix().length()!=0))
			buffer.append(getSuffix() + " ");
		return  buffer.toString();
	}

	
	public abstract String getSurname();
	
	public abstract String getGiven();
	
	public abstract String getSuffix();
	
	public abstract String getPrefix();
	
	public abstract void setSurname(String surname);
	
	public abstract void setGiven(String given);
	
	public abstract void setSuffix(String suffix);
	
	public abstract void setPrefix(String prefix);
	
	
}
