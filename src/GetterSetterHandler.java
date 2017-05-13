import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

public class GetterSetterHandler 

{
//	static Map<String, Boolean> getterSetterFieldIdentifier = new HashMap<String, Boolean>();
//	static Map<String, Boolean> getterSetterMethodIdentifier = new HashMap<String, Boolean>();
//	static String getterBody ="";
//	static String setterBody ="";
	static List<String>	getterSetterFields = new ArrayList<>();
	static List<String>	getterSetterMethods = new ArrayList<>();
	static boolean isGetter = false;
	static boolean isSetter = false;
	
	public static void getGetterSetterMethods()
	{
		for(String name:JavaFileParser.methodNameList)
		{
			int methodNameIndex = JavaFileParser.methodNameList.indexOf(name);
			//GetterSetterHandler.handleGetterSetterMethods(name,methodNameIndex);
			if(name.startsWith("get"))
				isGetter=isGetterSetterMethod(name,methodNameIndex);	
			
			if(name.startsWith("set"))
				isSetter=isGetterSetterMethod(name,methodNameIndex);
			
			if(isGetter && isSetter)
			{
			//	System.out.println("call to handle getter setter fields");
				handleGetterSetterFields();
				
			}
			
			//	System.out.println(isGetter);
			//	System.out.println(isSetter);
		}
	}
	
	public static void handleGetterSetterFields()
	{
		
		for(String field: JavaFileParser.fieldNameList)
		{
			field=field.substring(1, field.length()-1);
			for(String method:getterSetterMethods)
			{
				//System.out.println("getter setter method -- " + method);
				
				
				//System.out.println("getter setter field -- " + field);
				if(method.toLowerCase().contains(field))
				{
					if(!getterSetterFields.contains(field))
						getterSetterFields.add(field);
					//System.out.println("getter setter fields -- "+getterSetterFields);
				}
			}
		}
	}
	
//	public static boolean isGetterMethod(String name, int index)
//
//	{
//		
//			//System.out.println(name.toUpperCase());
//		//	System.out.println(name);
//		//	if(!name.toUpperCase().startsWith("GET"))
//				//return false;
//		//	if(!JavaFileParser.methodParameterList.get(index).isEmpty())
//			//	return false;
//		//	if(JavaFileParser.methodReturnTypeList.get(index).equals("void"))
//			//	return false;
//			
//			
//			getterBody = JavaFileParser.methodBodyList.get(index);
//			getterBody=getterBody.substring(2,getterBody.length()-2);
//			getterSetterMethods.add(name);
//			System.out.println(getterBody);
//		
//			return true;
//	}
//	
//	public static boolean isSetterMethod(String name, int index)
//
//	{
//		
//		//	System.out.println(name.toUpperCase());
//			
//			if(!name.toUpperCase().startsWith("SET"))
//				return false;
//			if(JavaFileParser.methodParameterList.get(index).isEmpty())
//				return false;
//			if(!JavaFileParser.methodReturnTypeList.get(index).equals("void"))
//				return false;
//			setterBody = JavaFileParser.methodBodyList.get(index);
//			setterBody=setterBody.substring(2,setterBody.length()-2);
//			getterSetterMethods.add(name);
//			System.out.println(setterBody);
//		
//			return true;
//	}
	
	public static boolean isGetterSetterMethod(String methodName,int methodNameIndex)
	{

		for(String fieldName: JavaFileParser.fieldNameList)
		{	
			
			
			
			
			fieldName=fieldName.substring(1, fieldName.length()-1);
	//		System.out.println("inside is getter setter method -- fieldname -- "+fieldName);
			
			if(methodName.toUpperCase().equals("GET"+fieldName.toUpperCase()) )
			{
		//		System.out.println("method name equal to fieldNAme");
				getterSetterMethods.add(methodName);
				
				isGetter= true;
			//	handleGetterSetterFields(fieldName);
				return isGetter;
						
			}
			
			if( methodName.toUpperCase().equals("SET"+fieldName.toUpperCase()) )
			{
				getterSetterMethods.add(methodName);
				isSetter=true;
			//	handleGetterSetterFields(fieldName);
				return isSetter;
			}
			
			
			
//			if(getterBody.contains(fieldName) && setterBody.contains(fieldName))
//			{
//				getterSetterFields.add(fieldName);
//			}
			
		}
		
		return false;
	}
	
}
