An interpreter for Lox programming language, based on the book Crafting Interpreters by Robert Nystrom.

## Features
- **Tokens**: Tokenization and lexical analysis.
- **Parser**: Recursive descent parsing.
- **AST**: Abstract Syntax Tree representation.
- **Interpreter**: Executes Lox code in a tree-walk interpreter.
- **Expressions**: Arithmetic, logical, comparison, and grouping expressions.
- **Statements**: Print, expression, and variable declarations.
- **Variables**: Global and local variable scopes.
- **Control Flow**: `if`, `else`, `while`, and `for` loops.
- **Error Reporting**: Syntax and runtime error detection with helpful messages.

## Sample Code in Lox
```lox
// Variables and print statements
var x = 10;
var y = 20;
print x + y; // Output: 30

// Conditional
if (x < y) {
  print "x is less than y";
} else {
  print "x is greater than or equal to y";
}

// While loop
var count = 3;
while (count > 0) {
  print count;
  count = count - 1;
}

// For loop
for (var i = 0; i < 5; i = i + 1) {
  print i;
}
```
