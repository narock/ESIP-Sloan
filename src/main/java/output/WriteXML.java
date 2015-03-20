package output;

import data.Agu;
import data.Person;
import java.util.ArrayList;

public class WriteXML {
	
	private FileWrite fw = new FileWrite ();
	
	public void writeFile ( String outputDir, String year, Agu data ) {
	
		final String newline = System.getProperty("line.separator");
		
		String[] parts = data.getAbstractUri().split("/");
		String fileName = outputDir + parts[ parts.length-1 ] + ".xml";
		
		String session = parts[9];
		String section = parts[7];
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + newline + 
					 "<Abstract>" + newline + 
					 "  <URI>" + data.getAbstractUri() + "</URI>" + newline + 
					 "  <Year>" + year + "</Year>" + newline + 
					 "  <Session>" + session + "</Session>" + newline + 
					 "  <Section>" + section + "</Section>" + newline + 
					 "  <Text>" + data.getAbstractText() + "</Text>" + newline + 
					 "  <Keywords>" + newline;
				
					 ArrayList <String> keywords = data.getKeywordsAsText();
					 for (int i=0; i<keywords.size(); i++ ) {
					    xml = xml + "    <Keyword>" + keywords.get(i) + "</Keyword>" + newline;
					 }
					 
		 xml = xml + "  </Keywords>" + newline + 
					 "  <Authors>" + newline; 
					 
					 ArrayList <Person> authors = data.getExpandedAuthors();
					 for (int i=0; i<authors.size(); i++ ) {
						Person p = authors.get(i);
						// check for null values
						if ( (p.getName() != null) && (p.getEmail() != null) ) {
					       xml = xml + "	  <Author>" + newline + 
					                   "      <Name>" + p.getName().trim() + "</Name>" + newline + 
					                   "      <Email>" + p.getEmail().trim() + "</Email>" + newline + 
					                   "    </Author>" + newline; 
						}
					 }
					 
		 xml = xml + "  </Authors>" + newline + 
					 "</Abstract>";
		
		fw.newFile( fileName, xml );
		
	}
	
}