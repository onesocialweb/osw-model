package org.onesocialweb.model.vcard4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public class DefaultNicknameField extends NicknameField implements Serializable{

	private List<AclRule> rules = new ArrayList<AclRule>();
	
	private String nickname;

	@Override
	public String getNickname() {		
		return nickname;
	}

	@Override
	public void setNickname(String nickname) {
		this.nickname=nickname;
		
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
