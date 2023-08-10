lexer grammar MxLexer;

Add : '+';
Sub : '-';
Mul : '*';
Div : '/';
Mod : '%';

Greater : '>';
Less : '<';
GreaterEqual : '>=';
LessEqual : '<=';
NotEqual : '!=';
Equal : '==';

And : '&&';
Or : '||';
Not : '!';

RightShift : '>>';
LeftShift : '<<';
BitAnd : '&';
BitOr : '|';
BitXor : '^';
BitNot : '~';

Assign : '=';

Increase : '++';
Decrease : '--';

Dot : '.';
Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';
Quite : '"';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Void : 'void';
Bool : 'bool';
Int : 'int';
String : 'string';
New : 'new';
Class : 'class';
Null : 'null';
True : 'true';
False : 'false';
This : 'this';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';

Identifier : [a-zA-Z][a-zA-Z0-9_]*;
ConstantInt : [1-9][0-9]* | '0';
ConstantStr : '"' ('\\n'|'\\\\'|'\\"'|.)*? '"';

Whitespace : [ \t]+ -> skip;
Newline : ('\r' '\n'? |'\n') -> skip;
BlockComment : '/*' .*? '*/' -> skip;
LineComment : '//' ~[\r\n]* -> skip;