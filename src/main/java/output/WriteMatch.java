package output;

public class WriteMatch {
	
	private int counter = 0;
	private FileWrite fw = new FileWrite ();
	
	private String getName ( String d ) {
		
		String[] parts = d.split(":");;
		String name = parts[0].trim();
		return name;
		
	}

	public void writeMatch ( String outputDir, String data1, String data2, String technique ) {
	
		final String newline = System.getProperty("line.separator");
		
		String fileName = outputDir + technique + "_" + counter + ".xml";
		counter++;
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + newline + 
					 "<Match>" + newline + 
					 "  <Name1>" + getName(data1) + "</Name1>" + newline + 
					 "  <Name2>" + getName(data2) + "</Name2>" + newline + 
					 "  <Technique>" + technique + "</Session>" + newline + 
					 "</Match>";
		
		fw.newFile( fileName, xml );
		
	}
	
}