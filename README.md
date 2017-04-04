# CMPE202-uml-parser

This project is to convert a input java code into UML diagram . Use javaparser to read the file and obtain the abstract tree structure. PlantUML is used to convert the tree structure into UML class diagram and Graphviz will then produce a image of the class diagram.

Tasks done:

1) Checked the functionality of java parser
2) Created an abstract syntax tree of the input code
3) Plantuml - verified successfully
4) Graphviz - verified successfully

Tasks left to do:

1) store complete data about class
2) method data needed to identify getter and setter methods
3) which method should be traversed -- find out to get dependency inside methods for interfaces
4) association - one to one and multiple associations --- how to find the difference?
5) Sequence diagram
