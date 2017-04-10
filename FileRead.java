import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitor; 
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class FileRead
{
	static Multimap<String, String> interfaceMethodData = ArrayListMultimap.create();
	static Multimap<String, String> classMethodData = ArrayListMultimap.create();
	static List<String> classNameData = new ArrayList<>();
	static List<String> interfaceNameData = new ArrayList<>();
	static boolean interfaceMethodsPresent = false;
	
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
			VoidVisitor<?> fieldVisitor = new JavaFileParser.FieldCollector();
			VoidVisitor<?> constructorVisitor = new JavaFileParser.ConstructorCollector();
			VoidVisitor<?> innerVariableVisitor = new JavaFileParser.InnerVariableCollector();
			UMLClassStringMaker umlClassStringMaker = new UMLClassStringMaker();
			UMLDiagramGenerator umlDiagramGenerator = new UMLDiagramGenerator();
			
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
						methodVisitor.visit(cu[i], null);
						if(JavaFileParser.isInterface )
						{
							interfaceNameData.add(JavaFileParser.interfaceNameList.get(0));
							if(!JavaFileParser.methodNameList.isEmpty())
							{
								interfaceMethodsPresent=true;
								for(String methodName: JavaFileParser.methodNameList)
								{
									interfaceMethodData.put(JavaFileParser.interfaceNameList.get(0), methodName);
								
								}
							}
							
							else
								interfaceMethodsPresent=false;
								
						}
						
						else
						{
							classNameData.add(JavaFileParser.classNameList.get(0));
							if(!JavaFileParser.methodNameList.isEmpty())
							{
								
								for(String methodName: JavaFileParser.methodNameList)
								{
									classMethodData.put(JavaFileParser.classNameList.get(0), methodName);
								
								}
							}
							
						}
						
						umlClassStringMaker.clear();
						
					}
					
				}

			}
			
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
						methodVisitor.visit(cu[i], null);
						fieldVisitor.visit(cu[i],null);
						constructorVisitor.visit(cu[i],null);
						innerVariableVisitor.visit(cu[i],null);
						umlClassStringMaker.setString();
						umlClassStringMaker.clear();
						
					}
					
				}

			}
			
			
			umlDiagramGenerator.classDiagramGenerator(UMLClassStringMaker.umlInputString.toString());
		
		}
}
