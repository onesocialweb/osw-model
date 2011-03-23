package org.onesocialweb.model.vcard4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.onesocialweb.model.acl.AclRule;

public class DefaultXFeedField extends XFeedField{

private List<AclRule> rules = new ArrayList<AclRule>();
	
	private String feed;
	
	@Override
	public String getFeed() {
		return feed;
	}

	@Override
	public void setFeed(String feed) {
		this.feed = feed;
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
