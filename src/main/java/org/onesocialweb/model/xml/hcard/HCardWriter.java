package org.onesocialweb.model.xml.hcard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.dom.DOMDocument;
import org.onesocialweb.model.vcard4.EmailField;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.TelField;
import org.onesocialweb.model.vcard4.URLField;
import org.onesocialweb.xml.namespace.HTML;
import org.onesocialweb.xml.namespace.VCard4;
import org.w3c.dom.Element;

public class HCardWriter {

	public String buildProfilePage(Profile profile, String username, String pathToWrite) throws IOException {
		String html="<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"> " +
				"<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
		if (profile.hasField(FullNameField.NAME))
			html+=profile.getFullName();
		else 
			html+=profile.getName();
		html+=" hCard profile @ OneSocialWeb" + "</title>";
		html+="<link rel=\"icon\" href=\"http://vodafonernd.com/favicon.ico\">";
		html+= "</head>" + "<body id=\"hcard\"><code><meta content=\"none\" name=\"decorator\" /></code><p>";
		html+=writeHCard(profile);
		html+="</body></html>";
		
		return html;
		
	//	serializePage(html, pathToWrite, username);
		
	}
	
	private String writeHCard(Profile profile){
		DOMDocument domDocument = new DOMDocument();	
		
	
		
		Element div = (Element) domDocument.appendChild(domDocument.createElement(HTML.DIV_ELEMENT));
		div.setAttribute(HTML.CLASS_ATTR, VCard4.VCARD_ELEMENT);
				

			if (profile.hasField(URLField.NAME)){
					Element a= (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.A_ELEMENT));
					a.setAttribute(HTML.CLASS_ATTR, URLField.NAME);
					a.setAttribute(HTML.HREF_ATTR, "http://"+profile.getUrl());
					if (profile.hasField(FullNameField.NAME)){
						a.removeAttribute(HTML.CLASS_ATTR);
						a.setAttribute(HTML.CLASS_ATTR, URLField.NAME + " " + FullNameField.NAME);
						a.appendChild(a.getOwnerDocument().createTextNode(profile.getFullName()));
					}
					div.appendChild(div.getOwnerDocument().createElement(HTML.P_ELEMENT));
			} else 	if (profile.hasField(FullNameField.NAME)) {
				Element span = (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.SPAN_ELEMENT));
				span.setAttribute(HTML.CLASS_ATTR, FullNameField.NAME);
				span.appendChild(span.getOwnerDocument().createTextNode(profile.getFullName()));
			}
			if (profile.hasField(PhotoField.NAME)){
				Element img= (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.IMG_ELEMENT));
				img.setAttribute(HTML.SRC_ATTR, profile.getPhotoUri());
				img.setAttribute(HTML.CLASS_ATTR, PhotoField.NAME);
				img.setAttribute(HTML.ALT_ATTR, PhotoField.NAME);
				img.setAttribute(HTML.HEIGHT_ATTR, "100");
				img.setAttribute(HTML.WIDTH_ATTR, "100");
				div.appendChild(div.getOwnerDocument().createElement(HTML.BR_ELEMENT));
			}
			if (profile.hasField(NoteField.NAME)){
				Element span = (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.SPAN_ELEMENT));
				span.setAttribute(HTML.CLASS_ATTR, NoteField.NAME);
				span.appendChild(span.getOwnerDocument().createTextNode(profile.getNote()));
				div.appendChild(div.getOwnerDocument().createElement(HTML.BR_ELEMENT));
			}
			if (profile.hasField(EmailField.NAME)){
				Element a= (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.A_ELEMENT));
				a.setAttribute(HTML.CLASS_ATTR, EmailField.NAME);
				a.setAttribute(HTML.HREF_ATTR, "mailto:"+profile.getEmail());
				a.appendChild(a.getOwnerDocument().createTextNode(profile.getEmail()));
			}
			if (profile.hasField(TelField.NAME)){
				Element telDiv = (Element) div.appendChild(div.getOwnerDocument().createElement(HTML.DIV_ELEMENT));
				telDiv.setAttribute(HTML.CLASS_ATTR, TelField.NAME);
				telDiv.appendChild(telDiv.getOwnerDocument().createTextNode(profile.getTel()));
			}
			
			return domDocument.asXML();
	}
		
	public void serializePage(String html, String path, String username) throws IOException {
		FileWriter fstream = new FileWriter(path + "/" + username + ".html");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(html);
    //	Close the output stream
        out.close();
	}
		
	
}
