NOTE: This is a class project that I did a few years ago.  

PROJECT TITLE: Hydro
PURPOSE OF PROJECT: A simple programming language
VERSION or DATE: 1.0
HOW TO START THIS PROJECT: Run Runner.main();
AUTHORS: Ryan Ray

------------------------------------------------------------------------

NAVIGATING

Because I don't have bluej at home and this project has 63 classes I have
provided a basic guide to the classes.  


Tokenizer -> Breaks a String of text into lexical parts.
             For example given "var 123" the tokenizer would output
             "var" " " "123"

Parser -> The parser interprets the stream of tokens and builds it into
          a data strcutue called an Abstract Syntax Tree (AST for short).

Symbol -> Symbols tell the parser how to handle specific tokens.  The
          symbol class is abstract all classes with Symbol in the name
          are implementations of Symbol.

AstNode -> AstNode is a single node in the AST and represents one operation
           or value.  All files with Node in the name are AstNodes.

Command -> Command is an interface which represents a single command that
           can be run by the evaluator. 

Source -> Is a queryable representaion of a source file which is used
          to print error messages.

Evaluator -> This evaluates a list of commands and maintains the call stack.

HydroError -> This represents an error that occured somewere in execution.

HydroObject -> This is the base class for all values used in Hydro

HydroContext -> Combines the tokensizer, parser, and evaluator together
                to esily run code.

The following classes are interfaces exposed in Hydro: 
 * HydroString
 * HydroNumber
 * ObjectProto
 * HydroBoolean
 * HydroModule
 * HydroContextInterface
 * HydroResult
 * HydroUndefined
 * HydroSystem

------------------------------------------------------------------------

DOCUMENTATION

This section is provided to explain the syntax of Hydro.  

Any line starting with a # is a comment

semicolons are required after all statements

numbers can be writted as
    5;
    2.5;

Negitive numbers can not be entered (so -5 will not work)
All numbers a treated as doubles so
    3 / 2 == 1.5;


strings can be written as
    "string";
    
Escape codes are not supported


Varaible names can include letters and the following symbols
! % ^ * $ & - _ + = | / < >
although single = is reseved for assignments.

Be aware that a=b is a variable name while a = b is an assignment.


Operators are acutaly methods on the object to the left so
    1 + 2;
is the same as
    1.+(2);
    
Function calls and methods work the same way as in java
    fn(param);
    fn();
    object.method(paramA, paramB);


Variables are declared when they are first used
    a = 1;
fields are added to objects when they are used
    obj.a = 1;


if expressions work similarly to java except they have no perenthisis or else if

if condition {
    # true clause
};

Remember the semicolon at the end is required.

if condition {
    # true clause
}
else {
    # false clause
};


functions can be defined with the function expression

func(paramA, paramB) {
    # function body
};

the return value is the last value used in a function so

func() {
    true;
};

would return true


Since all every thing in Hydro in an expression you can do things like

foo = func() {
    # some code
};

name = if isTall { "Fred"; } else { "Bob"; };

bar(func() {
    # called by bar
});

bar = func(fn) {
    fn();
};


Hydro uses prototypical inheritence so if a field is not found on an object 
hydro will search the prototype for that field.

Object.foo = 5; # prototype of all values

bar = Object.create(); # create a new object with Object as its prototype

bar.foo == 5;

1.foo == 5; # because all values inherit from Object anything defined on object
            # exists on all values even Undefined

someUndefinedVariable.foo == 5;

self is a secial variable which points to the object a function was called on

foo.bar = func() {
    self == foo;
};


A prototype chain is all the prototype of an object and that objects prototype
and so on.  In the representaions of a prototype chain the arrows(<-) point
toward the prototype;


class ObjectProto - prototype of all objects
    Exposed as:  Object
    Methods:
        * &&(other) - logical and based on the toBoolean method
        * ||(other) - logical or based on the toBoolean method
        * ++(other) - concatinate string (calls toString)
        * toString() - converts this object to a string
        * !=(other) - calls the == method and inverts the result
        * ==(other) - checks if 2 objects are the same reference
        * get(name) - gets the name specified from the object
        * set(name, value) - sets the name specified on the object
        * has(name) - checks if the name exists on an object
        * ownKeys() - returns a List of field names on this object
        * keys() - returns a List of field names on this object and its prototype

class HydroString - represents a string
    Exposed As:  String
    Methods:
        * length() - returns the length of a string
        * ==(other) - checks if two string are the same
        * ++(other) - concatinates 2 strings
        * toNumber() - converts a string to a number

class HydroNumber - represents a number
    Exposed As:  Number
    Methods:
        * floor() - rounds down
        * +(other) - same as java
        * -(other) - same as java
        * *(other) - same as java
        * /(other) - same as java
        * %(other) - same as java
        * ==(other) - same as java
        * <(other) - same as java
        * >(other) - same as java
        * <=(other) - same as java
        * >=(other) - same as java
        * toChar() - converts a number to its equivolent charactar

class HydroBoolean - represents a boolean
    Exposed As:  Boolean
    Methods:
        * &&(other) - logical and
        * ||(other) - logical or
        * not() - Inverts the boolean (same as ! in java)

class HydroModule - Exposes the require function
        require is used to load a local file
        (returns the exports variable from the loaded file)

class HydroUndefined - represents all empty values
    Exposed As: Undefined
    Methods: none

class HydroContextInterface - alows evaluation of code
    Returned by System.createContext
    Methods:
        eval(code) - Executes a string of code and returns the result
    
class HydroResult - the result of code being evaluated
    Returned by HydroContextInterface.eval
    Methods:
        success - Field representing success or failure of the evaluated code
        getMessage() - Returns the error message
        printError() - Prints the error that occured
        unwrap() - Returns the value of the evaluated code

class HydroSystem - a collection of tools
    Exposed As: System
    Methods:
        * print(value) - the same as System.out.println
        * createContext() - the same as  new HydroContext()  in java
        * readLine(prompt) - prints prompt and reads a line
        * random(lower, upper) - generates a random number between lower and upper


class List (Function)
    defined in stdlib.txt
    Static methods:
        * from(listLike) - converts an object with a length field and index fields to a list
    Instance methods:
        * add(item) - add an item to a list
        * remove(index) - remove an item from a list
        * forEach(fn) - calls fn with the value and the index for every item in the list
