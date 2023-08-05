/**
 * Write your info here
 *
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

grammar Task8;

/**
 * This rule is to check your grammar using "ANTLR Preview"
 */
test: /* (Rule1 | Rule2 | ... | RuleN)+ */ EOF; //Replace the non-fragment lexer rules here

// Write all the necessary lexer rules and fragment lexer rules here
IF: 'if' | 'If' | 'iF' | 'IF';
ELSE: 'else' | 'elsE' | 'elSe' | 'elSE' | 'eLse' | 'eLsE' | 'eLSe' | 'eLSE' |
'Else' | 'ElsE' | 'ElSe' | 'ElSE' | 'ELse' | 'ELsE' | 'ELSe' | 'ELSE' ;
//ELSE options{caseInsensitive=true;}: 'else';
COMP: '>' | '<' | '>=' | '<=' | '==' | '!=';
ID: [a-zA-Z_] [a-zA-Z0-9_]*;
NUM: DIGITS ('.' DIGITS)? (EXPONENT)?;
fragment DIGITS: [0-9]+;
fragment EXPONENT: [Ee] [+-]? DIGITS;
LIT: '"' ( ESC | ~[\\"\n\r] )* '"';
fragment ESC: '\\\\' | '\\"';
LP: '(';
RP: ')';
WS: [ \t\r\n]+ -> skip; // skip whitespace
fragment DIGIT: [0-9];

//ANYTHING: .;