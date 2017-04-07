import java.util.ArrayList;
import java.util.List;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import japa.parser.ast.type.ClassOrInterfaceType;

public class JavaFileParser 
{
	//StringBuilder plantUMLText = new StringBuilder();
	static ArrayList<String> classNameList = new ArrayList<String>();
	static ArrayList<String> interfaceNameList = new ArrayList<String>();
	static List<ClassOrInterfaceType> extendsList = new ArrayList<>();
	static List<ClassOrInterfaceType> implementsList = new ArrayList<>();
	static List<String> methodNameList = new ArrayList<>();
	static List<Integer> methodModifierList = new ArrayList<>();
	static List<String> methodReturnTypeList = new ArrayList<>();
	static List<String> methodParameterList = new ArrayList<>();
	static boolean isInterface;

	public static class ClassOrInterfaceCollector extends VoidVisitorAdapter<Void>
	
	{
		@Override
		public void visit(ClassOrInterfaceDeclaration cid, Void arg)
		{
			super.visit(cid,arg);
		//	System.out.println(cid.getName());
			
			if(cid.isInterface())
			{
				interfaceNameList.add(cid.getName());
				isInterface = true;
				//for(String interfaceName: JavaFileParser.interfaceNameList)
					System.out.println("\nInterface  ----  "+cid.getName());
			}
			else
			{
				isInterface = false;
				classNameList.add(cid.getName());
				System.out.println("\nClass  ----  "+cid.getName());
				
				if(cid.getExtends()!=null)
				{
					extendsList=cid.getExtends();
					System.out.println("extends" + cid.getExtends());
				}
				
				if(cid.getImplements()!=null)
				{
					implementsList=cid.getImplements();
					System.out.println("implements" + cid.getImplements());
				}
			}
			
		}
		
		public static class MethodCollector extends VoidVisitorAdapter<Void>
	
	{
		@Override
		public void visit(MethodDeclaration md, Void arg)
		
		{
			super.visit(md,arg);
			
			methodModifierList.add(md.getModifiers());
		//	System.out.print(JavaFileParser.methodModifierList);
			
				
				methodReturnTypeList.add(md.getType().toString());
		//		System.out.print(" "+JavaFileParser.methodReturnTypeList);
			
				methodNameList.add(md.getName());
		//		System.out.print(" "+JavaFileParser.methodNameList);
			
					if(md.getParameters()!=null)
					{
						methodParameterList.add(md.getParameters().toString());
						//System.out.print(" "+JavaFileParser.methodParameterList);
					}
					else
						methodParameterList.add("");
					//System.out.print(" "+JavaFileParser.methodParameterList);
		}
		
		
	}
		
		
	}
	
