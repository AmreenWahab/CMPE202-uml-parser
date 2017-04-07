import java.lang.*;
import java.util.Arrays;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;

public class UMLClassStringMaker
{

	static StringBuilder inputString = new StringBuilder();
	static StringBuilder umlInputString = new StringBuilder();
	
	public void setString()
	{

		inputString=new StringBuilder();
		classOrInterfaceNameString();
		attributeNameString();
		constructorNameString();
		methodNameString();
		inputString.append("}\n");
		umlInputString.append(inputString.toString());
	
	//	System.out.println(inputString);
	//	System.out.println(umlInputString);
		
	}
	
