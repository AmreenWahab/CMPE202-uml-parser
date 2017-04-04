import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.*;

public class Test 
	
{

	public static void main(String args[])
		
	{
		
	 String source = "@startuml \n" + "Class A" + "\n@enduml";
	 System.out.println(source);	
	SourceStringReader reader = new SourceStringReader(source);
	FileOutputStream png = null;
		
	try 
		{
			png = new FileOutputStream(new File("E:\\img4.png"));
			reader.generateImage(png,  new FileFormatOption(FileFormat.PNG));
		} 
		
	catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
	catch (IOException e)
	{
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		
  }
}

