import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.type.ClassOrInterfaceType;

public class UMLClassStringMaker
{

	static StringBuilder inputString = new StringBuilder();
	static StringBuilder umlInputString = new StringBuilder();
	//static List<HashMap<String,String>> mapExtendsList = new ArrayList<>();
	//static HashMap<String,List<String>> interfaceMethodList = new HashMap<>();	
	InheritanceRelationString inheritanceRelation = new InheritanceRelationString();
	InterfaceRelationString interfaceRelation = new InterfaceRelationString();
	//boolean isGetterSetterMethod = false;
	static Multimap<String, String> interfaceMethodList = ArrayListMultimap.create();
	

	public void setString()
	{
		
		inputString=new StringBuilder();
		classOrInterfaceNameString();
		if(!JavaFileParser.isInterface)
		{
			constructorNameString();
			GetterSetterHandler.getGetterSetterMethods();
		}
		if(!JavaFileParser.implementsList.isEmpty())
		{
			InterfaceDependency.removeClonedMethods();
		}
		methodNameString();
		attributeNameString();
		
		inputString.append("}\n");
		
		if(!JavaFileParser.extendsList.isEmpty())
			inheritanceRelation.extendsRelationship();
		
		if(!JavaFileParser.implementsList.isEmpty())
			interfaceRelation.implementsRelationship();
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
//			if(!JavaFileParser.methodNameList.isEmpty())
//			for(String methodName: JavaFileParser.methodNameList)
//			{
//				System.out.println("Inside copying method names to guava");
//				System.out.println("-----interface name - "+JavaFileParser.interfaceNameList.get(0));
//				System.out.println("-----method name - "+methodName);
//				interfaceMethodList.put(JavaFileParser.interfaceNameList.get(0), methodName);
//			
//			}
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
			
		
//			if((name.startsWith("[")) && (name.endsWith("]")))
//			{
				newname = name.substring(1, name.length()-1);
			//}
		AssociationRelationString.associationDependency(name);
		System.out.println("fieldname -- " +newname);
		System.out.println("asssFields -- " +AssociationRelationString.associationFields);
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
	
