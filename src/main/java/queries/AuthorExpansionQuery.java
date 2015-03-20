package queries;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import data.Person;

public class AuthorExpansionQuery extends Endpoints {
	
	private String createQueryString ( String authorUri ) {
			   
	   String query =
			"SELECT DISTINCT ?person ?name ?mbox where { " +
            "?person <http://tw.rpi.edu/schema/hasRole> <" + authorUri + "> . " +
            "?person <http://xmlns.com/foaf/0.1/name> ?name	. " +
            "?person <http://xmlns.com/foaf/0.1/mbox> ?mbox . " +
			"}";

	   return query;
	
	}

	public void testEndpoint () { this.testEndpoint( this.agu ); }
	
	public Person submitQuery( String authorUri ) {
		 		 
		 Person personObject = new Person ();
		 
		 String personID = null;
		 String personName = null;
		 String personEmail = null;
		 ResultSet results = queryEndpoint( this.agu, createQueryString( authorUri ) );
		 while (results.hasNext()) {
			 
   		   QuerySolution soln = results.nextSolution();
   		   RDFNode person = soln.get("?person");
   		   RDFNode name = soln.get("?name");
   		   RDFNode mbox = soln.get("?mbox");
   		   
   		   // Person ID
   		   personID = person.toString();
   		   String[] parts = personID.split("@");
   		   personID = parts[0].trim();
   		   
   		   // Person Name
   		   personName = name.toString();
   		   parts = personName.split("\\^");
   		   personName = parts[0].trim();
   		   
   		   // Person Email
   		   personEmail = mbox.toString();
   		   parts = personEmail.split("\\^");
   		   personEmail = parts[0].trim();
   		   
   		   personObject.setID( personID );
   		   personObject.setName( personName );
   		   personObject.setEmail( personEmail );
   		   
   	     } // end while
		 
 		 return personObject;
		 
	 }
	 	
}