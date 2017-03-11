import java.io.*;

import japa.parser.ParseException;

public class FileRead
{

		public static void main(String[] args) throws IOException, ParseException 
		
		{
			File[] javaFile = (new File (args[0])).listFiles();
			ListClassName list = new ListClassName();
			list.listClass(javaFile);
		}
		
	
}
