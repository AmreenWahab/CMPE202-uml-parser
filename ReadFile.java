import java.io.*;
public class ReadFile 
{
	public static void main(String[] args) 
	{
		
		FileReader javaFile = null;
		
		if (0 < args.length) {
	           try {
				javaFile = new FileReader(args[0]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	      }

	        BufferedReader breader = null;

	        try {

	            String crntLine;

	            breader = new BufferedReader(javaFile);

	            while ((crntLine = breader.readLine()) != null) {
	                System.out.println(crntLine);
	            }

	        } 

	        catch (IOException e) {
	            e.printStackTrace();
	        } 

	        finally 
	        {
	            try 
	            {
	                if (breader != null)
	                	breader.close();
	            } 
	            catch (IOException ex) 
	            {
	                ex.printStackTrace();
	            }
	        }
	    }
		
		

	}


