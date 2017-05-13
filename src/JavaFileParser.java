import java.util.ArrayList;
import java.util.List;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import japa.parser.ast.type.ClassOrInterfaceType;

public class JavaFileParser 
{

	static ArrayList<String> classNameList = new ArrayList<String>();
	static ArrayList<String> interfaceNameList = new ArrayList<String>();
	static List<ClassOrInterfaceType> extendsList = new ArrayList<>();
	static List<ClassOrInterfaceType> implementsList = new ArrayList<>();
	static List<String> methodNameList = new ArrayList<>();
	static List<Integer> methodModifierList = new ArrayList<>();
	static List<String> methodReturnTypeList = new ArrayList<>();
	static List<String> methodParameterList = new ArrayList<>();
	static List<String> fieldNameList = new ArrayList<>();
	static List<Integer> fieldModifierList = new ArrayList<>();
	static List<String> fieldTypeList = new ArrayList<>();
	static List<String> innerVariableTypeList = new ArrayList<>();
	static List<String> constructorParameterList = new ArrayList<>();
	static List<String> constructorNameList = new ArrayList<>();
	static List<Integer> constructorModifierList = new ArrayList<>();
	static List<String> methodBodyList= new ArrayList<>();
	
	static boolean isInterface;

	
	//collect class and interface name data
	public static class ClassOrInterfaceCollector extends VoidVisitorAdapter<Void>
	
	{
		@Override
		public void visit(ClassOrInterfaceDeclaration cid, Void arg)
		{
			super.visit(cid,arg);
		
			
			if(cid.isInterface())
			{
				interfaceNameList.add(cid.getName());
				isInterface = cid.isInterface();
			//	System.out.println("\nInterface  ----  "+cid.getName());
			}
			else
			{
				isInterface = cid.isInterface();
				classNameList.add(cid.getName());
		//		System.out.println("\nClass  ----  "+cid.getName());
				
				if(cid.getExtends()!=null)
				{
					extendsList=cid.getExtends();
					//System.out.println("extends" + cid.getExtends());
				}
				
				if(cid.getImplements()!=null)
				{
					implementsList=cid.getImplements();
				//	System.out.println("implements" + cid.getImplements());
				}
			}
			
		}
		
		
	}
	
	//collect method data
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
				
				if(!isInterface && md.getBody()!=null)
				methodBodyList.add(md.getBody().toString());
		//		System.out.print(" "+JavaFileParser.methodBodyList);
			
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
	
	//get fields data
	public static class FieldCollector extends VoidVisitorAdapter<Void>
	{
		@Override
		public void visit(FieldDeclaration fd, Void arg)
		
		{
			super.visit(fd,arg);
			
			fieldModifierList.add(fd.getModifiers());
			
			//System.out.println(fieldModifierList);
			
			fieldTypeList.add(fd.getType().toString());
			//System.out.println(fieldTypeList);
			
			fieldNameList.add(fd.getVariables().toString());
			
			
			//if(!fieldNameList.isEmpty())
			for(String name:fieldNameList)
			{
				if(name.contains("="))
				{
					
					String newname=name.substring(0, name.indexOf("="));
					//System.out.println("inside -----" + fieldNameList);
					fieldNameList.remove(name);
					//System.out.println("after removing -----" + fieldNameList);
					fieldNameList.add(newname);
					//System.out.println("after adding -----" + fieldNameList);
					
				}
						
			}  
			//System.out.println(fieldNameList);
		}
	}
	
	//gather constructor data
	
	public static class ConstructorCollector extends VoidVisitorAdapter<Void>
	{
		@Override
		public void visit(ConstructorDeclaration cd, Void arg)
		
		{
			super.visit(cd,arg);
			
			constructorModifierList.add(cd.getModifiers());
		//	System.out.print(JavaFileParser.constructorModifierList);
				
			constructorNameList.add(cd.getName());
			//System.out.print(" "+JavaFileParser.constructorNameList);
			
			if(cd.getParameters()!=null)
			{
			constructorParameterList.add(cd.getParameters().toString());
			//System.out.print(" "+JavaFileParser.constructorParameterList);
			}
			
			//System.out.print(" "+JavaFileParser.constructorParameterList);
		}
	}
	
	//to check for variable declaration inside methods
	
	public static class InnerVariableCollector extends VoidVisitorAdapter<Void>
	{
		@Override
		public void visit(VariableDeclarationExpr vd, Void arg)
		
		{
			super.visit(vd,arg);
								
			innerVariableTypeList.add(vd.getType().toString());
			 
//			System.out.println("InnerVariable "+innerVariableTypeList);
			
						
		}
	}
}
