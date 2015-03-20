package queries;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class KeywordsToTextQuery extends Endpoints {
	
	private String createQueryString ( String keyword ) {
			   
	   String query =
			"prefix purl: <http://purl.org/dc/terms/> " + 
			"SELECT ?subject WHERE { " + 
			"<" + keyword + "> purl:subject ?subject . " +			
			"}";

	   return query;
	
	}

	public void testEndpoint () { this.testEndpoint( this.agu ); }
	
	public String submitQuery( String keywordUri ) {
		 		 
		 String kwd = null;
		 ResultSet results = queryEndpoint( this.agu, createQueryString( keywordUri ) );
		 while (results.hasNext()) {
			 
   		   QuerySolution soln = results.nextSolution();
   		   RDFNode subject = soln.get("?subject");
   		   kwd = subject.toString();
   		   String[] parts = kwd.split("@");
   		   kwd = parts[0].trim();
   		   
   	     } // end while
		 
 		 return kwd;
		 
	 }
	 	
}