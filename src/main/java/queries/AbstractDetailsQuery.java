package queries;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import data.Agu;

public class AbstractDetailsQuery extends Endpoints {
	
	public ArrayList <Agu> getResults () { return data; }
	
	private ArrayList <Agu> data = new ArrayList <Agu> ();
	
	private String testQuery = 
			"PREFIX p1: <http://abstracts.agu.org/ontology#> " +
			"PREFIX p2: <http://data.semanticweb.org/ns/swc/ontology#> " +
			"PREFIX p3: <http://swrc.ontoware.org/ontology#> " +
			"PREFIX p4: <http://tw.rpi.edu/schema/> " +
			"PREFIX p5: <http://xmlns.com/foaf/0.1/> " +
			"select ?abstract where { " +
			"  ?abstract a p1:Abstract .  " +
			"} limit 2";
	
	private String createQueryString ( String year ) {
		
	   String uri = "<http://abstracts.agu.org/meetings/" + year + "/FM>";
	   
	   String query =
			"prefix p1: <http://abstracts.agu.org/ontology#> " +
			"prefix p2: <http://data.semanticweb.org/ns/swc/ontology#> " +
			"prefix p3: <http://swrc.ontoware.org/ontology#> " +
			"prefix p4: <http://tw.rpi.edu/schema/> " +
			"prefix p5: <http://xmlns.com/foaf/0.1/> " +

			"SELECT ?abstract ?text ?keyword ?author WHERE { " +

			"   ?abstract a p1:Abstract .  " +
			"   ?abstract p3:abstract ?text . " +
			"   ?abstract p2:hasTopic ?keyword . " +
			"   ?section p2:isSubEventOf " + uri + " . " + 
			"   ?section p1:section <http://abstracts.agu.org/sections/IN> . " +
			"   ?abstract p2:relatedToEvent ?section . " +
			"   ?abstract p4:hasAgentWithRole ?author . " +
			
			"}";

	   return query;
	
	}
					
	private boolean haveUri( String uri ) {
	
		boolean result = false;
		for (int i=0; i<data.size(); i++ ) {
		   Agu a = data.get(i);
		   if ( a.getAbstractUri().equals(uri) ) { result = true; break; }
		}
		return result;
		
	}
	
	private void replaceAbstract ( String uri, Agu agu ) {
		
		for (int i=0; i<data.size(); i++ ) {
		   Agu a = data.get(i);
		   if ( a.getAbstractUri().equals(uri) ) { 
			  data.set(i, agu);
			  break;
		   }
		}
		
	}
	
	private Agu getAbstractObject ( String uri ) {
		
		Agu agu = null;
		for (int i=0; i<data.size(); i++ ) {
		   Agu a = data.get(i);
		   if ( a.getAbstractUri().equals(uri) ) { agu = a; break; }
		}
		return agu;
		
	}
	
	public void testQueryToEndpoint ( String year ) { 
		
	   System.out.println( testQuery );
	   ResultSet results = queryEndpoint( this.agu, testQuery );
	   while ( results.hasNext() ) { 
   		  QuerySolution soln = results.nextSolution();
	      RDFNode abstractUri = soln.get("?abstract");
	      System.out.println( abstractUri.toString() );
	   }
	}

	public void testEndpoint () { this.testEndpoint( this.agu ); }
	
	public void submitQuery( String year ) {
		 		 
		 ResultSet results = queryEndpoint( this.agu, createQueryString( year ) );
		 while (results.hasNext()) {
			 
   		   QuerySolution soln = results.nextSolution();
   		   RDFNode abstractUri = soln.get("?abstract");
   		   RDFNode keyword = soln.get("?keyword");
   		   RDFNode author = soln.get("?author");
   		   RDFNode text = soln.get("?text");
   		
   		   // we are going to get the same uri multiple times 
		   // if we already have this uri then check if we need to add the current
		   // author or keyword
   		   Agu a;
		   if ( !haveUri(abstractUri.toString()) ) { 
		     a = new Agu (); 
		   } else {
		     a = getAbstractObject(abstractUri.toString());
		   }
   	 
		   // clean up the abstract text
		   String absText = text.toString();
		   absText = absText.replace("<", "&lt;");
		   absText = absText.replace(">", "&gt;");
		   
		   a.setAbstractUri( abstractUri.toString() );
		   a.setAbstractText( absText );
		   a.addAuthor( author.toString().trim() );
		   a.addKeyword( keyword.toString().trim() );
		   
		   if ( !haveUri(abstractUri.toString()) ) { 
			 data.add( a );
		   } else {
			 replaceAbstract( abstractUri.toString(), a );
		   }
		   
   	     } // end while
		 
	 }
	 	
}