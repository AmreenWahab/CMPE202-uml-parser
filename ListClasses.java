import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
//import parser.JavaParser;
//import com.github.javaparser.ast.CompilationUnit;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;


public class ListClassName 
{

		//private String name=null;
		
	 public void listClass(File[] files) throws IOException, ParseException
		{
		 
//		 for (File file : files){
//
//				if (!file.isDirectory() && file.getName().contains(".java")){
//	             		
//					 FileInputStream in = new FileInputStream(file);
//					 CompilationUnit cu;
//				        try {
//				            cu = JavaParser.parse(in);
//				        } finally {
//				            in.close();
//				        }
//				        
//				      //  System.out.println(cu);
//				        
//				      		        
//				 }
//			}
	
		 
		 
			//CompilationUnit[] cmp=new CompilationUnit[files.length];
			FileInputStream classnames=null;
			
			for(int i = 0; i < files.length; ++i)
			{
	            if(files[i].getName().contains("A.java"))
	            {
	                classnames = new FileInputStream(files[i]);
	               // cmp[i] = JavaParser.parse(classnames);
	                classnames.close();
	                
	                //for(String j:classnames)
	               System.out.println(classnames);
	           
	                 
	            }
        }
		
//			for(CompilationUnit i:cmp)
//			{
//				System.out.println(i);
//			}
			try{
			new VoidVisitorAdapter<Object>() {
				
				@Override
                public void visit(ClassOrInterfaceDeclaration cid, Object arg) {
                    super.visit(cid, arg);
                    System.out.println(" * " + cid.getName());
                }
            }.visit(JavaParser.parse(classnames), null);
            System.out.println(); // empty line
			
		}
			catch (ParseException e) {
                new RuntimeException(e);
            }
		
			
}
}
