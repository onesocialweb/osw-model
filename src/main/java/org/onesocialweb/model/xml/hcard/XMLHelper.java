package org.onesocialweb.model.xml.hcard;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;


public class XMLHelper {
	
	public static Node clearSoup(InputStream is){

		try{
			//we clear up the HTML and make it nice XML so we can finally parse it...

			Tidy tidy = new Tidy(); 
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			Document tidyDOM = tidy.parseDOM(is, null);	
			return tidyDOM.getDocumentElement();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
		

}
