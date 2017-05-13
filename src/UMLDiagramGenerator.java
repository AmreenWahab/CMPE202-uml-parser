import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.sourceforge.plantuml.*;

public class UMLDiagramGenerator {

	public void classDiagramGenerator(String inputData)
	{
		
	// String outputPath=	
	 String source = "@startuml \n" +"skinparam classAttributeIconSize 0\n"+ inputData + "\n@enduml";
	 System.out.println(source);
		
		SourceStringReader reader = new SourceStringReader(source);
	

		FileOutputStream png = null;
		try 
		{
			
		//	new File("outputdir").mkdir();
			
			png = new FileOutputStream(new File(FileRead.outputFileName+".png"));
			
			reader.generateImage(png,  new FileFormatOption(FileFormat.PNG));
		} 
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

