package org.onesocialweb.model.vcard4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public class DefaultEmailField extends EmailField
{
	
	private EmailField.Type type = EmailField.Type.Home;
	
	private List<AclRule> rules = new ArrayList<AclRule>();
	
	private String email;
	
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public void setEmail(String email, Type type) {
		this.email = email;
		this.type= type;
	}
	
	@Override
	public Type getType(){
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
