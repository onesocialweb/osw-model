package org.onesocialweb.model.vcard4;

import java.io.Serializable;

public abstract class NicknameField extends Field implements Serializable{

public static final String NAME = "nickname";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getValue() {
		return getNickname();
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	public abstract String getNickname();
	
	public abstract void setNickname(String nickname);
}
