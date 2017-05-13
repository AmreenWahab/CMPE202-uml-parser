import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;

public class InterfaceRelationString 

{
	static List<HashMap<String,String>> mapImplementsList = new ArrayList<>();	
	
	public void implementsRelationship()
	{
		for (ClassOrInterfaceType name: JavaFileParser.implementsList)
		{
			HashMap<String, String> implementName=new HashMap<>();
			implementName.put("Interface", name.getName());
		//	System.out.println("Interface -- "+name.getName() );
			implementName.put("Class", JavaFileParser.classNameList.get(0));
		//	System.out.println("Class -- "+JavaFileParser.classNameList.get(0) );
				if(!implementName.isEmpty())				
					mapImplementsList.add(implementName);
		}
		
		if(!mapImplementsList.isEmpty())
		for(HashMap<String, String> item: mapImplementsList)
		{
			UMLClassStringMaker.inputString.append(item.get("Interface")).append("<|.. ").append(item.get("Class")).append("\n");
		}
	}
	
}
