package data;

import data.Person;
import java.util.ArrayList;

public class Agu {
	
	private String abstractUri;
	private String abstractText;
	private String session;
	private String section;
	private ArrayList <String> keywords = new ArrayList <String> ();
	private ArrayList <String> authors = new ArrayList <String> ();
	private ArrayList <String> keywordsAsText = new ArrayList <String> ();
	private ArrayList <Person> authorsExpanded = new ArrayList <Person> ();
	
	public ArrayList <String> getKeywordsAsText () { return keywordsAsText; }
	public ArrayList <Person> getExpandedAuthors () { return authorsExpanded; }
	
	public void setKeywordText ( ArrayList <String> k ) { keywordsAsText = k; }
	public void setExpandedAuthors ( ArrayList <Person> a ) { authorsExpanded = a; }
	
	public String getAbstractUri () { return abstractUri; }
	public String getAbstractText () { return abstractText; }
	public String getSession () { return session; }
	public String getSection () { return section; }
	public ArrayList <String> getKeywords () { return keywords; }
	public ArrayList <String> getAuthors () { return authors; }
	
	public void setAbstractUri ( String uri ) { abstractUri = uri; }
	public void setAbstractText ( String text ) { abstractText = text; }
	public void setSession ( String s ) { session = s; }
	public void setSection ( String s ) { section = s; }
	
	public void setKeywords ( ArrayList <String> k ) { keywords = k; }
	
	public void addKeyword ( String keyword ) { 
	  if ( !keywords.contains(keyword) ) {
		keywords.add(keyword); 
	  }
	}
	
	public void addAuthor ( String author ) { 
	  if ( !authors.contains(author) ) {
		authors.add(author); 
	  }
	}
	
}