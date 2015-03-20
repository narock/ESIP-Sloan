package main;

import data.Agu;
import data.Person;
import output.WriteXML;
import java.util.ArrayList;
import queries.AbstractDetailsQuery;
import queries.KeywordsToTextQuery;
import queries.AuthorExpansionQuery;

public class GetAguData {
	
	private boolean containsAuthor ( ArrayList <Person> authors, String id ) {
	
		boolean found = false;
		for ( int i=0; i<authors.size(); i++ ) {
		  Person p = authors.get(i);
		  if ( p.getID() != null ) {
			if ( p.getID().equals(id) ) { found = true; break; }
		  }
		}
		return found;
		
	}
	
	public static void main (String[] args) {
		
		GetAguData d = new GetAguData ();
		d.execute( args[0], args[1] );
		
	}
	
	public void execute ( String outputDir, String year ) {
	
		// Create the output object
		WriteXML xml = new WriteXML ();
		
		// Get all the AGU Conference Data for this year
		AbstractDetailsQuery query = new AbstractDetailsQuery ();
		query.submitQuery(year);
	    ArrayList <Agu> results = query.getResults();		
		
	    // Create the query objects
	    AuthorExpansionQuery authorQuery = new AuthorExpansionQuery ();
	    KeywordsToTextQuery keywordQuery = new KeywordsToTextQuery ();
	    
	    // Create ArrayLists to hold our results
	 	ArrayList <Person> expandedAuthors = new ArrayList <Person> ();
	 	ArrayList <String> keywordsAsText = new ArrayList <String> ();
	 			
	 	// Keep track of our status
	 	int twentyFive = (int) (.25 * results.size());
	 	int fifty = (int) (.50 * results.size());
	 	int seventyFive = (int) (.75 * results.size());
	 	
	    // Loop over each conference abstract in this year
		for (int i=0; i<results.size(); i++) {
		  
			// Where are we?
			if ( i == twentyFive ) { System.out.println("25% Complete..."); }
			if ( i == fifty ) { System.out.println("50% Complete..."); }
			if ( i == seventyFive ) { System.out.println("75% Complete..."); }
			if ( i == results.size()-1 ) { System.out.println("Done."); }
			
			// Reset the results ArrayLists
			expandedAuthors.clear();
			keywordsAsText.clear();
			    
			// Get the next conference abstract
			Agu a = results.get(i);
		  
			// Extract all the authors for this abstract
			ArrayList <String> authors = a.getAuthors();
			
			// We are initially given an author ID
			// We need to expand this into name and email address via another query
			for ( int j=0; j<authors.size(); j++ ) {
			   Person p = authorQuery.submitQuery( authors.get(j) );
			   if ( !containsAuthor( expandedAuthors, authors.get(j) ) ) { expandedAuthors.add(p); }
			}

			// Extract all the keywords for this abstract
			ArrayList <String> keywords = a.getKeywords();
			
			// We are initially given a keyword Id
			// We need to expand this into text values of keyword via another query
			for ( int j=0; j<keywords.size(); j++ ) {
			   String kwd = keywordQuery.submitQuery( keywords.get(j) );
			   if ( !keywordsAsText.contains(kwd) ) { keywordsAsText.add(kwd); }
			}
			
			// Put the expanded data back into the AGU object
			a.setExpandedAuthors( expandedAuthors );
			a.setKeywordText( keywordsAsText );
			results.set( i, a );
			
			// Write this abstract out to a file
			xml.writeFile( outputDir, year, a);
			
		} // end for loop over all abstracts for this year
		
	}
	
}