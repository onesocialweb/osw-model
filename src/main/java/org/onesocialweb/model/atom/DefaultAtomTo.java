package org.onesocialweb.model.atom;

public class DefaultAtomTo implements AtomTo{

	private String uri;
	
	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public void setUri(String uri) {
		this.uri=uri;		
	}

}
