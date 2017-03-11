import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ListClasses 
{
	private String name=null;
	
	public void listClass(File[] files) throws IOException
	{
		CompilationUnit[] cmp=new CompilationUnit[files.length];
		FileInputStream classnames=null;
		
		for(int i = 0; i < files.length; ++i)
		{
            if(files[i].getName().contains(".java"))
            {
                classnames = new FileInputStream(files[i]);
                cmp[i] = JavaParser.parse(classnames);
                classnames.close();
                
            }
        }
		
		System.out.println(classnames);
		
		
	}
	

}
