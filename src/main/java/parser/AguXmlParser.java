package parser;

import data.Person;
import java.io.FileReader;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class AguXmlParser extends DefaultHandler {
 
	String uri = null;
	String name = null;
    String email = null;
    
    StringBuffer accumulator = new StringBuffer();
    ArrayList <Person> authors = new ArrayList <Person> ();
   
    public ArrayList <Person> parse ( String file ) throws Exception {
    	
    	XMLReader xr = XMLReaderFactory.createXMLReader();
        AguXmlParser handler = new AguXmlParser();
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);
        FileReader r = new FileReader(file);
        xr.parse(new InputSource(r));
        return handler.authors;
        
    }

    public AguXmlParser() { super(); }

    ////////////////////////////////////////////////////////////////////
    // Event handlers.
    ////////////////////////////////////////////////////////////////////

     /** Method which tells the XML parser what to do at the start of each element
	  * 
	  * @param  uri  the uri of the element
	  * @param  name  the name of the element
	  */
    public void startElement (String uri, String name,
		      String qName, Attributes atts) {
    	
    	accumulator.setLength(0); // Ready to accumulate new text
    	    	
    }
    
     /** Method which tells the XML parser what to do at the end of each element
	  * 
	  * @param  uri  the uri of the element
	  * @param  name  the name of the element
	  * @param  qName  the fully qualified name of the element
	  */
    public void endElement (String uri, String name, String qName) {

      String d = accumulator.toString().trim();
      if ( name.equals("URI") ) this.uri = d;
  	  if ( name.equals("Name") ) this.name = d;
  	  if ( name.equals("Email") ) {
  		  this.email = d;
  		  Person p = new Person ();
  		  p.setName( this.name );
  		  p.setEmail( this.email );
  		  p.setID( this.uri );
  		  this.authors.add( p );
  	  }
	        
    }
    
     /** Method for parsing the character data of the XML file
	  *
	  */
    public void characters (char ch[], int start, int length) { accumulator.append(ch, start, length); }

}
