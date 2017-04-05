import java.io.*;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitor; 
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class FileRead
{
	public static class ClassNamePrinter extends VoidVisitorAdapter<Void>
	
	{
		@Override
		public void visit(ClassOrInterfaceDeclaration cid, Void arg)
		{
			super.visit(cid,arg);
			System.out.println(cid.getName());
		}
		
		
	}
	
		public static void main(String[] args) throws IOException, ParseException 
		
		{
			System.out.println(args[0]);
	
			File folder = new File(args[0]);
			File[] files = folder.listFiles();
	
			int nooffiles = files.length;
			
		//	VoidVisitor<?> classNameVisitor = new ClassNamePrinter();
			
			VoidVisitor<?> classOrInterfaceVisitor = new JavaFileParser.ClassOrInterfaceCollector();	
			VoidVisitor<?> methodVisitor = new JavaFileParser.MethodCollector();
						
			CompilationUnit[] cu = new CompilationUnit[nooffiles];

			
			for (int i = 0; i < files.length; i++) 
			{
				if (files[i].getName().contains(".java"))
				{
					FileInputStream in = new FileInputStream(files[i]);
					cu[i] = JavaParser.parse(in);
					in.close();
					
					if (cu[i] != null) 
					{
						classOrInterfaceVisitor.visit(cu[i], null);
						
						//for()
						
						methodVisitor.visit(cu[i], null);
						
					}
					
					
				}
//				
//				if(JavaFileParser.isInterface)
//				{
//					for(String interfaceName: JavaFileParser.interfaceNameList)
//						System.out.println("Interface  ----  "+interfaceName);
//					
//					System.out.println("Methods in Interface");
//					
//					for(int j=0; j<=JavaFileParser.methodNameList.size();j++ )
//					{
//						System.out.print(JavaFileParser.methodModifierList.get(i));
//						System.out.print(" "+JavaFileParser.methodReturnTypeList.get(i));
//						System.out.print(" "+JavaFileParser.methodNameList.get(i));
//						System.out.print(" "+JavaFileParser.methodParameterList.get(i));
//						
//					}
//					
//				}
//				
//				else
//				{
//					
//					for(String className: JavaFileParser.classNameList)
//						System.out.println("Class  ----  "+className);
//					
//					System.out.println("Methods in Class");
//					
//					for(int j=0; j<=JavaFileParser.methodNameList.size();j++ )
//					{
//						System.out.print(JavaFileParser.methodModifierList.get(i));
//						System.out.print(" "+JavaFileParser.methodReturnTypeList.get(i));
//						System.out.print(" "+JavaFileParser.methodNameList.get(i));
//						System.out.print(" "+JavaFileParser.methodParameterList.get(i));
//						
//					}
//					
//				}
//				
			}
		
		}
		
	
}
