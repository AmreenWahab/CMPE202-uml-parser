import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import japa.parser.ast.body.ModifierSet;

public class UMLClassStringMaker
{

	static StringBuilder inputString = new StringBuilder();
	static StringBuilder umlInputString = new StringBuilder();	
	InheritanceRelationString inheritanceRelation = new InheritanceRelationString();
	InterfaceRelationString interfaceRelation = new InterfaceRelationString();
	static Multimap<String, String> interfaceMethodList = ArrayListMultimap.create();
	

	//make a call to each class,method,constructor and fields to make a string that plantuml accepts
	
	public void setString()
	{
		
		inputString=new StringBuilder();
		classOrInterfaceNameString();
		
		//interface does not have constructors and getter setter method features
		if(!JavaFileParser.isInterface)
		{
			constructorNameString();
			GetterSetterHandler.getGetterSetterMethods();
		}
		
		//remove cloned methods from a class only if it implements an interface
		if(!JavaFileParser.implementsList.isEmpty())
		{
			InterfaceDependency.removeClonedMethods();
		}
		
		methodNameString();
		attributeNameString();
		
		inputString.append("}\n");
		
		//check for inheritance only if a class extends another class
		if(!JavaFileParser.extendsList.isEmpty())
			inheritanceRelation.extendsRelationship();
		
		//check for implementation only if a class implements another interface
		if(!JavaFileParser.implementsList.isEmpty())
			interfaceRelation.implementsRelationship();
		
		//check for dependency only if its a class
		if(!JavaFileParser.isInterface)
		{
		InterfaceDependency.checkDependency();
		InterfaceDependency.constructDependencyUMLString();
		}
		umlInputString.append(inputString.toString());
	
	//	System.out.println(inputString);
	//	System.out.println(umlInputString);
		
	}
	
	public void classOrInterfaceNameString()
	
	{
		if(inputString==null)
		{
			inputString.append("@startuml\n").append("skinparam classAttributeIconSize 0");
			
		}
		
		if(JavaFileParser.isInterface)
		{
			inputString.append("interface ");
			inputString.append(JavaFileParser.interfaceNameList.get(0));
			inputString.append(" {\n");
//				System.out.println("Inside copying method names to guava");
//				System.out.println("-----interface name - "+JavaFileParser.interfaceNameList.get(0));
//				System.out.println("-----method name - "+methodName);
//				interfaceMethodList.put(JavaFileParser.interfaceNameList.get(0), methodName);
		}
		
		else
		{
			inputString.append("class ");
			inputString.append(JavaFileParser.classNameList.get(0));
			inputString.append(" {\n");
		}
		
	//	attributeNameString();
	}
	
	public void attributeNameString()

	{
		
		for(String name:JavaFileParser.fieldNameList)
		{
			String newname="";
			
			newname = name.substring(1, name.length()-1);
	
			AssociationRelationString.associationDependency(name);
//		System.out.println("fieldname -- " +newname);
//		System.out.println("asssFields -- " +AssociationRelationString.associationFields);
			if(AssociationRelationString.associationFields.contains(newname))
			{
				continue;
			}
		
		if(GetterSetterHandler.getterSetterFields.contains(newname))
		{
			inputString.append("+").append(newname).append(":").append(JavaFileParser.fieldTypeList.get(JavaFileParser.fieldNameList.indexOf(name))).append("\n");
			continue;
		}
		
		if(ModifierSet.isPublic(JavaFileParser.fieldModifierList.get(JavaFileParser.fieldNameList.indexOf(name))))
		{
			inputString.append("+").append(newname).append(":").append(JavaFileParser.fieldTypeList.get(JavaFileParser.fieldNameList.indexOf(name))).append("\n");
		}
		
		else if(ModifierSet.isPrivate(JavaFileParser.fieldModifierList.get(JavaFileParser.fieldNameList.indexOf(name))))
		{
			inputString.append("-").append(newname).append(":").append(JavaFileParser.fieldTypeList.get(JavaFileParser.fieldNameList.indexOf(name))).append("\n");
		}
			
		}
		
	//	System.out.println(inputString);
	//	methodNameString();
	}
	
	public void constructorNameString()
	
	{
		for(String name:JavaFileParser.constructorNameList)
		{
			int constructorNameIndex = JavaFileParser.constructorNameList.indexOf(name);
				
				String parameter = "";
				if(!JavaFileParser.constructorParameterList.isEmpty())
				{
						String[] multipleParameters;
						String[] parameterParts;
						String finalParameter="";
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
								finalParameter += parameterParts[1]+":"+parameterParts[0];
								//constructorParaData.add(parameterParts[0]);
								if(!p.trim().equals(multipleParameters[multipleParameters.length-1].trim()))
								{
									finalParameter += " , ";
								}
							}
							
						}
						else
						{
							parameter=parameter.trim();
							parameterParts = parameter.split(" ");
							//parameter = parameterPart[1]+":"+parameterPart[0];
							finalParameter += parameterParts[1]+":"+parameterParts[0];
							//constructorParaData.add(parameterParts[0]);
						}
					 
						parameter=finalParameter;
				}
				
				inputString.append("+")
				.append(name)
				.append("(")
				.append(parameter)
				.append(")")
				.append("\n");
			//	System.out.println(inputString);	
			
			
		}
	
	}
	
	public void methodNameString()
	
	{
			
		for(String name:JavaFileParser.methodNameList)
		{
			
			int methodNameIndex = JavaFileParser.methodNameList.indexOf(name);

			if(!JavaFileParser.implementsList.isEmpty())
			{
//				System.out.println("Method name: "+name);
//				System.out.println("cloned methods : " + InterfaceDependency.clonedMethods);
				if(InterfaceDependency.clonedMethods.contains(name))
					continue;
			}
			
			
			if(GetterSetterHandler.getterSetterMethods.contains(name))
				continue;
			
				
			if(ModifierSet.isPublic(JavaFileParser.methodModifierList.get(methodNameIndex)))
			{
				
				
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
							//	methodParaData.add(parameterParts[0]);
								if(!p.trim().equals(multipleParameters[multipleParameters.length-1].trim()))
								{
									finalParameter += " , ";
								}
							}
							
						}
						else
						{
							parameter=parameter.trim();
							parameterParts = parameter.split(" ");
							//parameter = parameterPart[1]+":"+parameterPart[0];
							finalParameter += parameterParts[1]+":"+parameterParts[0];
							System.out.println("method par 0 : "+ parameterParts[0]);
							//methodParaData.add(parameterParts[0]);
						}
					 
						parameter=finalParameter;
				}
				
//				if (name.toUpperCase().contains("GET") || name.toUpperCase().contains("SET") )
//				{
//					
//				}
				
				inputString.append("+")
				.append(name)
				.append("(")
				.append(parameter)
				.append(")")
				.append(":")
				.append(JavaFileParser.methodReturnTypeList.get(methodNameIndex))
				.append("\n");
			//	System.out.println(inputString);	
			
			}
		}
	}

	
	public void clear()
	{
		
		JavaFileParser.classNameList.clear();
		JavaFileParser.interfaceNameList.clear();
		JavaFileParser.fieldModifierList.clear();
		JavaFileParser.fieldTypeList.clear();
		JavaFileParser.fieldNameList.clear();
		JavaFileParser.constructorModifierList.clear();
		JavaFileParser.constructorNameList.clear();
		JavaFileParser.constructorParameterList.clear();
		JavaFileParser.methodModifierList.clear();
		JavaFileParser.methodReturnTypeList.clear();
		JavaFileParser.methodNameList.clear();
		JavaFileParser.methodBodyList.clear();
		JavaFileParser.methodParameterList.clear();
		JavaFileParser.extendsList.clear();		
		JavaFileParser.implementsList.clear();
		JavaFileParser.innerVariableTypeList.clear();
		InterfaceRelationString.mapImplementsList.clear();
		InheritanceRelationString.mapExtendsList.clear();
		InterfaceDependency.dependencyList.clear();
		InterfaceDependency.methodParaData.clear();
		InterfaceDependency.constructorParaData.clear();
		
	}


}
