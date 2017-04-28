import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import japa.parser.ast.type.ClassOrInterfaceType;

public class InterfaceDependency 

{
	static List<String> clonedMethods = new ArrayList<String>(); 
	static SetMultimap<String, String> dependencyList = HashMultimap.create();
	static List<String> constructorParaData = new ArrayList<>();
	static List<String> methodParaData = new ArrayList<>();
	static List<String> parameterList = new ArrayList<>();
	
	public static void removeClonedMethods()

	{
		for(ClassOrInterfaceType interfaceName: JavaFileParser.implementsList)
		{
//			System.out.println("inside removeCM");
//			System.out.println("Interface Name : "+interfaceName);
//			System.out.println("Guava for each interface : "+FileRead.interfaceMethodData.get(interfaceName.toString()));
			clonedMethods=(List<String>) FileRead.interfaceMethodData.get(interfaceName.toString());
		}
	}
	
	public static void checkDependency()
	
	{
//		constructorParaData= parameterType(JavaFileParser.constructorNameList,JavaFileParser.constructorParameterList);
//		methodParaData= parameterType(JavaFileParser.methodNameList,JavaFileParser.methodParameterList);
		
		methodparameterType();
		constructorparameterType();
//		System.out.println("Constructor Para Data : "+constructorParaData);
//		System.out.println("Method Para Data : "+methodParaData);
//		System.out.println("InnerVariable List : "+JavaFileParser.innerVariableTypeList);
//		System.out.println("All Interface : "+FileRead.interfaceNameData);
		for(String interfaceName: FileRead.interfaceNameData)
		{
//			for(String methodName: JavaFileParser.methodNameList)
//			{
//				if(methodName.equals("main"))
//				{
							
					for(String name:JavaFileParser.innerVariableTypeList)
					{		
					
						//System.out.println(name);
				
						if(name.equals(interfaceName))
						{
							//System.out.println("inner variable : "+name+" is equal to a interface : "+interfaceName);

							dependencyList.put(JavaFileParser.classNameList.get(0), interfaceName);
						}
				
					}
//				}
//			}
			if(!constructorParaData.isEmpty())
			{		
				//System.out.println(name);
				
				if(constructorParaData.contains(interfaceName))
				{
					//System.out.println( "constructor para contains interface : "+interfaceName);
					dependencyList.put(JavaFileParser.classNameList.get(0), interfaceName);
				}
				
			}
			
			if(!methodParaData.isEmpty())
			{		
				//System.out.println(name);
				
				if(methodParaData.contains(interfaceName))
				{
					//System.out.println( "method para contains interface : "+interfaceName);
					dependencyList.put(JavaFileParser.classNameList.get(0), interfaceName);
				}
				
			}
		
		}
		
		
	}

	public static void methodparameterType()
	{
//		System.out.println("parameterList before clear : "+parameterList );
//		System.out.println("method name/constructor name : "+methods );
//		System.out.println("para name: "+parameters );
//		parameterList.clear();
//		System.out.println("parameterList after clear : "+parameterList );
		for(String name:JavaFileParser.methodNameList)
		{
			
			int methodNameIndex = JavaFileParser.methodNameList.indexOf(name);	
			String parameter = "";

				if(!JavaFileParser.methodParameterList.get(methodNameIndex).equals(""))
				{
						String[] multipleParameters;
						String[] parameterParts;
						String finalParameter="";
						parameter = JavaFileParser.methodParameterList.get(methodNameIndex).trim() ;
						
						if(parameter.contains("["))
							parameter=parameter.replace("[","");
						
						if(parameter.contains("]"))
							parameter=parameter.replace("]","");
					//	System.out.println(parameter);
						
						if(parameter.contains(","))
						{
							multipleParameters=parameter.split(",");
							for(String p:multipleParameters)
							{
								p=p.trim();
							
								parameterParts= p.split(" ");
								finalParameter += parameterParts[1]+":"+parameterParts[0];
							//	System.out.println("method par 0 : "+ parameterParts[0]);
								methodParaData.add(parameterParts[0]);
								
							}
							
						}
						else
						{
							parameter=parameter.trim();
							parameterParts = parameter.split(" ");
							methodParaData.add(parameterParts[0]);
						}
					 
						parameter=finalParameter;
				}
				
			}
		
	}
	
	public static void constructorparameterType()
	{
//		System.out.println("parameterList before clear : "+parameterList );
//		System.out.println("method name/constructor name : "+methods );
//		System.out.println("para name: "+parameters );
//		parameterList.clear();
//		System.out.println("parameterList after clear : "+parameterList );
		for(String name:JavaFileParser.constructorNameList)
		{
			int constructorNameIndex = JavaFileParser.constructorNameList.indexOf(name);			
			String parameter = "";

				//if(!JavaFileParser.constructorParameterList.get(constructorNameIndex).isEmpty())
			if(!JavaFileParser.constructorParameterList.isEmpty())
			{
						String[] multipleParameters;
						String[] parameterParts;
					//	String finalParameter="";
						parameter = JavaFileParser.constructorParameterList.get(constructorNameIndex).trim() ;
						
						if(parameter.contains("["))
							parameter=parameter.replace("[","");
						
						if(parameter.contains("]"))
							parameter=parameter.replace("]","");
					//	System.out.println(parameter);
						
						if(parameter.contains(","))
						{
							multipleParameters=parameter.split(",");
							for(String p:multipleParameters)
							{
								p=p.trim();
							
								parameterParts= p.split(" ");
								//finalParameter += parameterParts[1]+":"+parameterParts[0];
								constructorParaData.add(parameterParts[0]);
								
							}
							
						}
						else
						{
							parameter=parameter.trim();
							parameterParts = parameter.split(" ");
//							//parameter = parameterPart[1]+":"+parameterPart[0];
//							finalParameter += parameterParts[1]+":"+parameterParts[0];
							constructorParaData.add(parameterParts[0]);
						}
					 
				}
			}	
			
		
		//return parameterList;
	}
	
	public static void constructDependencyUMLString()
	{
		System.out.println("Dependency -- "+dependencyList);
		
		List<String> keysList = null;
		int count =0;
		String key1 ="";
		String key2="";
		
		for(String key:dependencyList.keys())
		{
			if(count ==0)
			{
				key1=key;
			}
			if(count == 1)
			{
				key2=key;
				if(key2.equals(key1))
					continue;
			}
			
			
		
			
			System.out.println("key -- "+key);
			for(String value : dependencyList.get(key)) 
		 	{
				System.out.println("value -- "+value);
			   UMLClassStringMaker.inputString.append(key).append(" ..> ").append(value).append("\n");
			   
			}
			
			count++;
		}
	}
}
