package org.onesocialweb.model.vcard4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public class DefaultTelField extends TelField
{

	private TelField.Type type = TelField.Type.VOICE;
	
	private String tel;
	
	private List<AclRule> rules = new ArrayList<AclRule>();
	
	
	@Override
	public String getNumber() {
		if (tel.contains("tel:"))
			return tel.substring(4);
		return tel;
	}

	@Override
	public void setNumber(String tel, Type type) {
		this.tel = tel;
		this.type=type;
	}
	
	public Type getType()
	{
		return this.type;
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
	public void removeAclRule(AclRule rule) {
		rules.remove(rule);
	}

	@Override
	public void setAclRules(List<AclRule> rules) {
		this.rules = rules;
	}
	
	@Override
	public boolean hasAclRules() {
		if (rules != null && rules.size() > 0) {
			return true;
		}
		return false;
	}
}
