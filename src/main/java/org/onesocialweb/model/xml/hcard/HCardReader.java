package org.onesocialweb.model.xml.hcard;

import org.onesocialweb.model.vcard4.DefaultVCard4Factory;
import org.onesocialweb.model.vcard4.Field;
import org.onesocialweb.model.vcard4.FullNameField;
import org.onesocialweb.model.vcard4.NicknameField;
import org.onesocialweb.model.vcard4.NoteField;
import org.onesocialweb.model.vcard4.PhotoField;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.model.vcard4.URLField;
import org.onesocialweb.model.vcard4.VCard4Factory;
import org.onesocialweb.model.vcard4.exception.CardinalityException;
import org.onesocialweb.model.vcard4.exception.UnsupportedFieldException;
import org.onesocialweb.xml.namespace.VCard4;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HCardReader {

	private final VCard4Factory factory;
	private String baseUrl;
	
	
	public HCardReader(String baseUrl){	
		this.factory=getProfileFactory();
		this.baseUrl=baseUrl;
	}
	
	public Profile readProfile(Node node){
		Profile profile =factory.profile();
		
		Node body=null;
		NodeList allNodes=node.getChildNodes();
		for (int i=0;i<allNodes.getLength();i++){
			if (allNodes.item(i).getNodeType() == Node.ELEMENT_NODE){ 
				if (allNodes.item(i).getNodeName().equals("body"))
					body=allNodes.item(i);
			}
		}
	
		Node hcard=findNestedDiv(body);
		
	
		profile=setProfile(hcard);		
					
		return profile;	
	}
	
	private Node findNestedDiv(Node start){
		Node nestedDiv=null;
		NodeList nodes= start.getChildNodes();
		
		for (int i=0; i<nodes.getLength() && nestedDiv==null; i++){
					
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE){ 
			Element div=  (Element) nodes.item(i);
			
			 String classValues=div.getAttribute("class");
			 if ((classValues!=null) && (classValues.contains("vcard")))
					nestedDiv=div;			 
			 else {
				 NodeList children=div.getChildNodes();
				 for (int j=0; j<children.getLength() && nestedDiv==null ; j++){
					 if (children.item(j).getNodeType() == Node.ELEMENT_NODE)
						 nestedDiv=findNestedDiv((Element)children.item(j));
				 }
			 }
			 
		}
		}

		return nestedDiv;
	}
	
	private Profile setProfile(Node hcardNode){
		final Profile profile =factory.profile();
		NodeList nodes = hcardNode.getChildNodes();
		try {
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element entity= (Element)nodes.item(i);
					Field f=extractFields(entity);
					if (f!=null){ 

						profile.addField(f);

					}
				}
			}
			profile.addField(factory.source("ostatus"));
		} catch (UnsupportedFieldException e){

		}catch (CardinalityException ce){

		}
		return profile;
	}
	
	private Field extractFields(Element e){
		Field f= null;

		String classAttr=e.getAttribute("class");
		if (match(classAttr, VCard4.PHOTO_ELEMENT))
			f=readPhoto(e);
		else if (match(classAttr, VCard4.NICKNAME_ELEMENT))
			f=readNickName(e);
		else if (match(classAttr, VCard4.FN_ELEMENT))
			f=readFullName(e);
		else if (match(classAttr, VCard4.URL_ELEMENT))
			f=readUrl(e);
		else if (match(classAttr, VCard4.NOTE_ELEMENT))
			f=readNote(e);
		else if (f==null){
			NodeList children=e.getChildNodes();
			for (int i=0;i<children.getLength() && f==null; i++){
				if (children.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element child=(Element)children.item(i);					
					f=extractFields(child);
				}
			}
		}					
		return f;
	}
	
	private FullNameField readFullName(Element e){
		FullNameField field=(FullNameField)factory.fullname();
		field.setFullName(e.getChildNodes().item(0).getNodeValue());
			
		return field;
	}
	
	private NoteField readNote(Element e){
		NoteField field=(NoteField)factory.note();		
		field.setNote(e.getChildNodes().item(0).getNodeValue());
			
		return field;
	}
	
	private URLField readUrl(Element e){
		URLField field=(URLField)factory.url();
		field.setURL(e.getAttribute("href"));
			
		return field;
	}
	
	private NicknameField readNickName(Element e){
		NicknameField field=factory.nickname();
		field.setNickname(e.getChildNodes().item(0).getNodeValue());
				
		return field;
	}
	
	private PhotoField readPhoto(Element e){
		PhotoField field=factory.photo();
		String photoUrl=e.getAttribute("src");
		if (!photoUrl.startsWith("http")){
			if (baseUrl.startsWith("http://www.google.com"))
				baseUrl="http://www.google.com";
			photoUrl=baseUrl+photoUrl;
		}
			
		field.setUri(photoUrl);
		
				
		return field;
	}
	
	private boolean match(String classAttr, String fieldName ){
		if (classAttr.equals(fieldName))
			return true;
		String[] values=classAttr.split("\\s+");
		for (String v: values){
			if (v.equalsIgnoreCase(fieldName))
				return true;
		}
		return false;
	}
	
	private VCard4Factory getProfileFactory() {
		return new DefaultVCard4Factory();
	}
}
