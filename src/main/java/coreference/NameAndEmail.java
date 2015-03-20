package coreference;

import output.WriteMatch;

import java.util.ArrayList;

public class NameAndEmail {
	
	private WriteMatch match = new WriteMatch ();
	
	private String[] getNameAndEmail ( String d ) {
		
		String[] parts = d.split(":");
		String email = parts[1].trim();
		parts = parts[0].split(",");
		String lastName = parts[0].trim();
		String[] result = {lastName, email};
		return result;
		
	}
	
	public void findSamePerson ( String outputDir, ArrayList <String> data ) {
				
		// Keep track of our status
		int twentyFive = (int) (.25 * data.size());
		int fifty = (int) (.50 * data.size());
		int seventyFive = (int) (.75 * data.size());
			 	
		for (int i=0; i<data.size(); i++ ) {
			
			// Where are we?
			if ( i == twentyFive ) { System.out.println("25% Complete..."); }
			if ( i == fifty ) { System.out.println("50% Complete..."); }
			if ( i == seventyFive ) { System.out.println("75% Complete..."); }
			if ( i == data.size()-1 ) { System.out.println("Done."); }
						
			String[] nameEmail = getNameAndEmail( data.get(i) );
			
			for (int j=0; j<data.size(); j++ ) {
				
				// make sure we're comparing different data
				if ( !data.get(i).equals(data.get(j)) ) {
					
					String[] nameEmail2 = getNameAndEmail( data.get(j) );
					
					if ( (nameEmail[0].equals("Narock")) && nameEmail2[1].contains("narock") ) { 
						System.out.println(nameEmail[0] + " " + nameEmail[1] + " " + nameEmail2[0] + " " + nameEmail2[1]); 
					} 

					//match.writeMatch(outputDir, data.get(i), data.get(j), "same_lastname_same_email");
					
				}
				
			}
			
		}
	}
	
}