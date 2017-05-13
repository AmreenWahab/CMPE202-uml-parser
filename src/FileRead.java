import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.visitor.VoidVisitor; 


public class FileRead
{
	static Multimap<String, String> interfaceMethodData = ArrayListMultimap.create();
	static Multimap<String, String> classMethodData = ArrayListMultimap.create();
	static List<String> classNameData = new ArrayList<>();
	static List<String> interfaceNameData = new ArrayList<>();
	static boolean interfaceMethodsPresent = false;
	static String outputFileName;
	
	public static void main(String[] args) throws IOException, ParseException 
		
	{
			System.out.println(args[0]);
			
			File folder = new File(args[0]);
			
			System.out.println("folder name "+folder.getName());
			outputFileName=folder.getName();
			
			File[] files = folder.listFiles();
	
			int nooffiles = files.length;
			
			VoidVisitor<?> classOrInterfaceVisitor = new JavaFileParser.ClassOrInterfaceCollector();	
			VoidVisitor<?> methodVisitor = new JavaFileParser.MethodCollector();
			VoidVisitor<?> fieldVisitor = new JavaFileParser.FieldCollector();
			VoidVisitor<?> constructorVisitor = new JavaFileParser.ConstructorCollector();
			VoidVisitor<?> innerVariableVisitor = new JavaFileParser.InnerVariableCollector();
			UMLClassStringMaker umlClassStringMaker = new UMLClassStringMaker();
			UMLDiagramGenerator umlDiagramGenerator = new UMLDiagramGenerator();
			
			CompilationUnit[] cu = new CompilationUnit[nooffiles];

			//collect all class names and interface names first to help in processing for dependency and association
			
			for (int i = 0; i < files.length; i++) 
			{
				if (files[i].getName().contains(".java"))
				{
					System.out.println(files[i].getName());
					FileInputStream in = new FileInputStream(files[i]);
					try{
					cu[i] = JavaParser.parse(in);
					}
					catch(ParseException e)
					{
						System.out.println(e);
					}
					in.close();
					System.out.println(files[i].getName());
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
							if(!JavaFileParser.classNameList.isEmpty())
								classNameData.add(JavaFileParser.classNameList.get(0));
							if(!JavaFileParser.methodNameList.isEmpty())
							{
								
								for(String methodName: JavaFileParser.methodNameList)
								{
									if(!JavaFileParser.classNameList.isEmpty())
									classMethodData.put(JavaFileParser.classNameList.get(0), methodName);
								
								}
							}
							
						}
						
						umlClassStringMaker.clear();
						
					}
					
				}

			}
			
//			parse completely through each and every class
			
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
			
//			System.out.println("input string "+UMLClassStringMaker.inputString);
//			System.out.println("ass string "+AssociationRelationString.drawAssociationString);
			
			//check for association
			
			AssociationRelationString.drawAssociation();
			
			//generate UML diagrams
			
			umlDiagramGenerator.classDiagramGenerator(UMLClassStringMaker.umlInputString.toString());
			
//			System.out.println(AssociationRelationString.associationList);
//			System.out.println(AssociationRelationString.associationFields);
			
			
		}
}
