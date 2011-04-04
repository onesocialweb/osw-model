package org.onesocialweb.model.cache;

public class DefaultDomainCache implements DomainCache {

	private String domain;
	private String protocols;
	
	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getProtocols() {
		return protocols;
	}

	@Override
	public void setProtocols(String protocols) {
		this.protocols = protocols;
	}
	
}
