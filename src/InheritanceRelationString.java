import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;

public class InheritanceRelationString 
{
	static List<HashMap<String,String>> mapExtendsList = new ArrayList<>();

	
	public void extendsRelationship()
	{
		for (ClassOrInterfaceType name: JavaFileParser.extendsList)
		{
			HashMap<String, String> extendName=new HashMap<>();
			extendName.put("Parent", name.getName());
		//	System.out.println("Parent -- "+name.getName() );
			extendName.put("Child", JavaFileParser.classNameList.get(0));
		//	System.out.println("Child -- "+JavaFileParser.classNameList.get(0) );
				if(!extendName.isEmpty())				
						mapExtendsList.add(extendName);
		}
		
		if(!mapExtendsList.isEmpty())
		for(HashMap<String, String> item: mapExtendsList)
		{
			UMLClassStringMaker.inputString.append(item.get("Parent")).append(" <|-- ").append(item.get("Child")).append("\n");
		}
	}
}
