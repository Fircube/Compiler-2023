parser grammar MxParser;

options { tokenVocab = MxLexer; }

program: ( funcDef | classDef | varDef )* EOF;

funcDef : returnType Identifier '(' parameter? ')' suite;
parameter : unitParameter (',' unitParameter)*;
unitParameter : defType Identifier;

classDef : Class Identifier '{' (varDef|classConstructor|funcDef)* '}'';';
classConstructor : Identifier '(' ')' suite;

varDef : defType unitVarDef (',' unitVarDef)* ';';
unitVarDef : Identifier ('=' expression)? ;

returnType : Void | defType;
defType : (baseType | Identifier) ('[' ']')*;
baseType : Bool | Int | String;

suite: '{' statement* '}';

realParameter : expression (',' expression)*;

statement
    : suite                                                 #block
    | varDef                                                #varDefStmt
    | If '(' expression ')' trueStmt=statement (Else falseStmt=statement)?
                                                            #ifStmt
    | While '(' expression ')' statement                    #whileStmt
    | For '(' ( varInit=varDef | (forInit=expression? ';')) (forCon=expression)? ';' (forStep=expression)? ')' statement
                                                            #forStmt
    | Return expression? ';'                                #returnStmt
    | Break ';'                                             #breakStmt
    | Continue ';'                                          #continueStmt
    | expression ';'                                        #exprStmt
    | ';'                                                   #emptyStmt
    ;

expression
    : primary                                               #atomExpr
    | expression '[' expression ']'                         #arrayExpr
    | expression op=Dot Identifier ('(' realParameter? ')')?
                                                            #memberExpr
    | expression '(' realParameter? ')'                     #funcCallExpr
    | expression postfix=(Increase | Decrease)              #postExpr // Post-increment/decrement operators
    | prefix=(Increase | Decrease) expression               #preExpr
    | prefix=(Not | BitNot | Add | Sub) expression          #unaryExpr // Unary operators
    | New (baseType | Identifier) (('[' expression ']')+ ('[' ']')* | ('('')')?)
                                                            #newExpr // object creation
    // ->* .*

    | expression op=(Mul | Div | Mod) expression            #binaryExpr // Multiplicative operators
    | expression op=(Add | Sub) expression                  #binaryExpr // Additive operators
    | expression op=(RightShift | LeftShift) expression     #binaryExpr // Shift operators
    | expression op=(Greater | Less | GreaterEqual | LessEqual) expression
                                                            #binaryExpr // Relational operators
    | expression op=(Equal | NotEqual) expression           #binaryExpr // Equality Operators
    | expression op=BitAnd expression                       #binaryExpr // Bitwise AND
    | expression op=BitXor expression                       #binaryExpr // Bitwise XOR
    | expression op=BitOr expression                        #binaryExpr // Bitwise OR
    | expression op=And expression                          #binaryExpr // Logic AND
    | expression op=Or expression                           #binaryExpr // Logic OR
    | <assoc=right> expression op='?' expression ':' expression
                                                            #ternaryExpr // Ternary
    | <assoc=right> expression op=Assign expression         #assignExpr // Assignment
    ;

primary
    : '(' expression ')'                                    #paren
    | Identifier                                            #identify
    | ConstantInt                                           #literal
    | ConstantStr                                           #literal
    | True                                                  #literal
    | False                                                 #literal
    | Null                                                  #literal
    | This                                                  #literal
    ;

