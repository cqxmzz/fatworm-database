grammar Fatworm;

options {
  output=AST;
  backtrack=true;
  memoize=true;
  k=1;
}

tokens {
  CREATE_DATABASE;
  USE_DATABASE;
  DROP_DATABASE;
  CREATE_TABLE;
  DROP_TABLE;
  CREATE_DEFINITION;
  PRIMARY_KEY;
  INSERT_VALUES;
  INSERT_COLUMNS;
  INSERT_SUBQUERY;
  UPDATE_PAIR;
  CREATE_INDEX;
  CREATE_UNIQUE_INDEX;
  DROP_INDEX;
  SELECT_DISTINCT;
  NOT_EXISTS;
}

@header        {package parser; }
@lexer::header {package parser; }

statement
  : database_statement
  | table_statement
  | insert_statement
  | delete_statement
  | update_statement
  | index_statement
  | select_statement
  ;

database_statement
  : CREATE DATABASE db_name
    -> ^(CREATE_DATABASE db_name)
  | USE db_name
    -> ^(USE_DATABASE db_name)
  | DROP DATABASE db_name
    -> ^(DROP_DATABASE db_name)
  ;

table_statement
  : CREATE TABLE tbl_name '(' create_definition (',' create_definition)* ')'
    -> ^(CREATE_TABLE tbl_name create_definition*)
  | DROP TABLE tbl_name (',' tbl_name)*
    -> ^(DROP_TABLE tbl_name*)
  ;

create_definition
  : col_name data_type column_definition_suffix*
    -> ^(CREATE_DEFINITION col_name data_type column_definition_suffix*)
  | PRIMARY KEY '(' col_name ')'
    -> ^(PRIMARY_KEY col_name)
  ;

data_type
  : INT^
  | FLOAT^
  | CHAR^ '('! INTEGER_LITERAL ')'!
  | DATETIME^
  | BOOLEAN^
  | DECIMAL^ '('! INTEGER_LITERAL (','! INTEGER_LITERAL)? ')'!
  | TIMESTAMP^
  | VARCHAR^ '('! INTEGER_LITERAL ')'!
  ;

column_definition_suffix
  : NOT? NULL^
  | DEFAULT^ const_value
  | AUTO_INCREMENT^
  ;

insert_statement
  : INSERT INTO tbl_name values_clause
    -> ^(INSERT_VALUES tbl_name values_clause)
  | INSERT INTO tbl_name '(' col_name (',' col_name)* ')' values_clause
    -> ^(INSERT_COLUMNS tbl_name col_name* values_clause)
  | INSERT INTO tbl_name '(' subquery ')'
    -> ^(INSERT_SUBQUERY tbl_name subquery)
  ;

values_clause
  : VALUES^ '('! value (','! value)* ')'!
  ;

delete_statement
  : DELETE^ FROM! tbl_name where_clause?
  ;

update_statement
  : UPDATE^ tbl_name SET! update_pair (','! update_pair)* where_clause?
  ;

update_pair
  : col_name '=' value
    -> ^(UPDATE_PAIR col_name value)
  ;

index_statement
  : CREATE INDEX index_name ON tbl_name '(' col_name ')'
    -> ^(CREATE_INDEX index_name tbl_name col_name)
  | CREATE UNIQUE INDEX index_name ON tbl_name '(' col_name ')'
    -> ^(CREATE_UNIQUE_INDEX index_name tbl_name col_name)
  | DROP INDEX index_name ON tbl_name
    -> ^(DROP_INDEX index_name tbl_name)
  ;

select_statement
  : SELECT select_expr (',' select_expr)* select_suffix*
    -> ^(SELECT select_expr* select_suffix*)
  | SELECT DISTINCT select_expr (',' select_expr)* select_suffix*
    -> ^(SELECT_DISTINCT select_expr* select_suffix*)
  ;

select_suffix
  : from_clause
  | where_clause
  | group_by_clause
  | having_clause
  | order_by_clause
  ;

from_clause
  : FROM^ tbl_ref (','! tbl_ref)*
  ;

where_clause
  : WHERE^ where_condition
  ;

group_by_clause
  : GROUP^ BY! col_name
  ;

having_clause
  : HAVING^ having_condition
  ;

order_by_clause
  : ORDER^ BY! order_by_col_name (','! order_by_col_name)*
  ;

order_by_col_name
  : col_name ASC^
  | col_name DESC^
  | col_name
  ;

select_expr
  : value (AS^ alias)?
  | '*'^
  ;

func
  : AVG | COUNT | MIN | MAX | SUM
  ;

col_name
  : (tbl_name '.'^)? name
  ;

tbl_ref
  : tbl_name (AS^ alias)?
  | '('! subquery ')'! AS^ alias
  ;

tbl_name
  : name
  ;

alias
  : name
  | func
  ;

index_name
  : name
  ;

db_name
  : name
  ;

name
  : ID
  ;

where_condition
  : bool_expr
  ;

having_condition
  : bool_expr
  ;

subquery
  : select_statement
  ;

bool_expr
  : logical_or
  ;

logical_or
  : logical_and (OR^ logical_and)+
  | logical_and
  ;

logical_and
  : primary (AND^ primary)+
  | primary
  ;

primary
  : value cop^ value
  | NOT EXISTS '(' subquery ')'
    -> ^(NOT_EXISTS subquery)
  | EXISTS^ '('! subquery ')'!
  | value IN^ '('! subquery ')'!
  | value cop ANY^ '('! subquery ')'!
  | value cop ALL^ '('! subquery ')'!
  | '('! bool_expr ')'!
  ;

cop
  : '<' | '>' | '=' | '<=' | '>=' | '<>'
  ;

value
  : multiplicative (('+'|'-')^ multiplicative)+
  | multiplicative
  ;

multiplicative
  : atom (('*'|'/'|'%')^ atom)+
  | atom
  ;

atom
  : '('! value ')'!
  | col_name
  | const_value
  | '('! subquery ')'!
  | func^ '('! col_name ')'!
  | '-'^ atom
  ;

const_value
  : INTEGER_LITERAL
  | STRING_LITERAL
  | FLOAT_LITERAL
  | TRUE
  | FALSE
  | NULL
  | DEFAULT
  ;

SELECT : S E L E C T ;
CREATE : C R E A T E ;
DATABASE : D A T A B A S E ;
DROP : D R O P ;
USE : U S E ;
INDEX : I N D E X ;
TABLE : T A B L E ;
PRIMARY : P R I M A R Y ;
KEY : K E Y ;
NOT : N O T ;
NULL : N U L L ;
DEFAULT : D E F A U L T ;
AUTO_INCREMENT : A U T O '_' I N C R E M E N T ;
INT : I N T ;
FLOAT : F L O A T ;
CHAR : C H A R ;
DATETIME : D A T E T I M E ;
BOOLEAN : B O O L E A N ;
DECIMAL : D E C I M A L ;
TIMESTAMP : T I M E S T A M P ;
VARCHAR : V A R C H A R ;
INSERT : I N S E R T ;
INTO : I N T O ;
VALUES : V A L U E S ;
DELETE : D E L E T E ;
FROM : F R O M ;
WHERE : W H E R E ;
UPDATE : U P D A T E ;
SET : S E T ;
UNIQUE : U N I Q U E ;
ON : O N ;
DISTINCT : D I S T I N C T ;
GROUP : G R O U P ;
BY : B Y ;
HAVING : H A V I N G ;
ORDER : O R D E R ;
ASC : A S C ;
DESC : D E S C ;
AS : A S ;
AVG : A V G ;
COUNT : C O U N T ;
MIN : M I N ;
MAX : M A X ;
SUM : S U M ;
AND : A N D ;
OR : O R ;
EXISTS : E X I S T S ;
ANY : A N Y ;
IN : I N ;
ALL : A L L ;
TRUE : T R U E ;
FALSE : F A L S E ;

ID: LETTER (LETTER | '0'..'9')*
  ;

STRING_LITERAL
  : '\'' ( ~('\\'|'\'') )* '\''
  ;

INTEGER_LITERAL
  : DIGIT+
  ;
  
FLOAT_LITERAL
  : INTEGER_LITERAL '.' INTEGER_LITERAL
  ;

WS: (' ' | '\r' | '\t' | '\u000C' | '\n' ) {$channel=HIDDEN;}
  ;

fragment
LETTER
  : 'a'..'z'
  | 'A'..'Z'
  | '_'
  ;

fragment
DIGIT
  : '0'..'9'
  ;

fragment A : 'a' | 'A';
fragment B : 'b' | 'B';
fragment C : 'c' | 'C';
fragment D : 'd' | 'D';
fragment E : 'e' | 'E';
fragment F : 'f' | 'F';
fragment G : 'g' | 'G';
fragment H : 'h' | 'H';
fragment I : 'i' | 'I';
fragment J : 'j' | 'J';
fragment K : 'k' | 'K';
fragment L : 'l' | 'L';
fragment M : 'm' | 'M';
fragment N : 'n' | 'N';
fragment O : 'o' | 'O';
fragment P : 'p' | 'P';
fragment Q : 'q' | 'Q';
fragment R : 'r' | 'R';
fragment S : 's' | 'S';
fragment T : 't' | 'T';
fragment U : 'u' | 'U';
fragment V : 'v' | 'V';
fragment W : 'w' | 'W';
fragment X : 'x' | 'X';
fragment Y : 'y' | 'Y';
fragment Z : 'z' | 'Z';

