package data;

public class Person {
	
	private String id;
	private String name;
	private String email;
	
	public String getID () { return id; }
	public String getName () { return name; }
	public String getEmail () { return email; }
	
	public void setID ( String i ) { id = i.trim(); }
	public void setName ( String n ) { name = n.trim(); }
	public void setEmail ( String e ) { email = e.trim(); }
	
}