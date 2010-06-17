package org.onesocialweb.model.vcard4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.onesocialweb.model.acl.AclRule;

public class DefaultTimeZoneField extends TimeZoneField
{

	private TimeZone timezone;
	
	private TimeZoneField.Type type = TimeZoneField.Type.TEXT;
	
	private List<AclRule> rules = new ArrayList<AclRule>();
	
	@Override
	public String getTimeZone() {
		if (timezone != null) {
			return timezone.getID();
		} else {
			return null;
		}
	}
	
	@Override
	public TimeZone getJavaTimeZone() {
		return timezone;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public void setTimeZone(String value, Type type) {
		TimeZone tz=TimeZone.getTimeZone(value);	
		setJavaTimeZone(tz);		
		this.type = type;
	}
	
	@Override
	public void setJavaTimeZone(TimeZone tz) {
		this.timezone = tz;		
	}
	
	@Override
	public void addAclRule(AclRule rule) {
		rules.add(rule);
	}
	
	@Override
	public List<AclRule> getAclRules() {
		return Collections.unmodifiableList(rules);
	}

	@Override
	public void setAclRules(List<AclRule> rules) {
		this.rules = rules;
	}

	@Override
	public void removeAclRule(AclRule rule) {
		rules.remove(rule);
	}

	@Override
	public boolean hasAclRules() {
		if (rules != null && rules.size() > 0) {
			return true;
		}
		return false;
	}

}
