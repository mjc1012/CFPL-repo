# CFPL-repo
A simple programming language with an interpreter developed using Java.

Program Structure:
- every line contains a single statement
- all variable declaration is found on top of the program
- executable code should be found inside the START and STOP block
- all reserved words are in capital letters
- sharp sign(#) signifies next line or carriage return
- ampersand(&) serves as a concatenator

Data Types:
1. INT – an ordinary number with no decimal part. It uses 32 bits. It can be positive or negative.
2. CHAR – a single symbol. It uses UNICODE.
3. BOOL – represents the literals true or false.
4. FLOAT – a number with decimal part. It uses 64 bits.

Operators:
Arithmetic operators
1. ( ) - parenthesis
2. *, /, % - multiplication, division, modulo
3. +, - - addition, subtraction

Unary operator
1. + - positive
2. - - negative

Sample Programs
1. Sample Program 1:

VAR xyz, abc=100 AS INT

START

xyz= ((abc *5)/10 + 10) * -1

OUTPUT: “[” & xyz & “]”

STOP

Output of the Sample Program 1:
[-60]

2. Sample Program 2:

VAR abc, b, c AS INT

VAR x, w_23=’w’ AS CHAR

VAR t=”TRUE” AS BOOL

START

abc=b=10

w_23=’a

OUTPUT: abc & “hi” & b & “#” & w_23

STOP

Output of the Sample Program 2:
10hi10
a

