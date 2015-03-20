package main;

import data.Person;
import java.io.File;
import java.util.Vector;
import java.util.ArrayList;
import parser.AguXmlParser;
import parser.GetListOfFiles;
import coreference.NameAndEmail;

public class ReadAguXml {
	
	public static void main (String[] args) {
	
		AguXmlParser parser = new AguXmlParser ();
		ArrayList <String> authorList = new ArrayList <String> ();		

		File dir = new File ( args[0] );
		GetListOfFiles fileReader = new GetListOfFiles ();
		Vector <String> files = fileReader.Process(dir);
			 	
		for ( int i=0; i<files.size(); i++ ) {
						
			try {
				
				ArrayList <Person> authors = parser.parse(files.get(i));
				for ( int j=0; j<authors.size(); j++ ) {
				   Person p = authors.get(j);
				   String entry = p.getName() + ":" + p.getEmail();
				   if ( !authorList.contains(entry) ) { authorList.add(entry); }
				}
				
			} catch ( Exception e) { System.out.println(e); }
			
		}
		
		System.out.println("Found " + authorList.size() + " authors.");
		
		// co-reference resolution using last name and email
		//NameAndEmail co = new NameAndEmail ();
		//co.findSamePerson(args[1], authorList);
		
	}
	
}