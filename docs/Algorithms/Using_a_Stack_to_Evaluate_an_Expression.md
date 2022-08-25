# Using a Stack to Evaluate an Expression

We often deal with arithmetic expressions written in what is called infix notation:
```		
         Operand1 op Operand2
```
We have rules to indicate which operations take precedence over others, and we often use parentheses to override those rules.

It is also quite possible to write arithmetic expressions using postfix notation:

         Operand1 Operand2 op
With postfix notation, it is possible to use a stack to find the overall value of an infix expression by first converting it to postfix notation.

Example: Suppose we have this infix expression Q:

         5 * ( 6 + 2 ) - 12 / 4
The equivalent postfix expression P is:

         5 6 2 + * 12 4 / -
This discussion assumes all our operations are binary operations (2 arguments each). Notice that we also sometimes use unary operations such as ++ or -- or the unary + and -.

We are not including the possibility of array elements in this discussion. (The subscript can be an expression which would have to be evaluated.)

One way to think of an expression is as a list or sequence of items, each of which is a left parenthesis, right parenthesis, argument, or operator. An argument can be a constant or the name of a variable. Presumably it would be necessary at some point to replace each variable with its value.

There are two algorithms involved. One converts an infix expression to postfix form, and the other evaluates a postfix expression. Each uses a stack.


## Transform an infix expression to postfix notation

Suppose Q is an arithmetic expression in infix notation. We will create an equivalent postfix expression P by adding items to on the right of P. The new expression P will not contain any parentheses.

We will use a stack in which each item may be a left parenthesis or the symbol for an operation.

     Start with an empty stack.  We scan Q from left to right. 

     While (we have not reached the end of Q)
        If (an operand is found)
           Add it to P
        End-If
        If (a left parenthesis is found) 
           Push it onto the stack
        End-If
        If (a right parenthesis is found) 
           While (the stack is not empty AND the top item is
                  not a left parenthesis)
              Pop the stack and add the popped value to P
           End-While
           Pop the left parenthesis from the stack and discard it 
        End-If
        If (an operator is found)
           If (the stack is empty or if the top element is a left
               parenthesis)
              Push the operator onto the stack
           Else  
              While (the stack is not empty AND the top of the stack 
                     is not a left parenthesis AND precedence of the                  
                     operator <= precedence of the top of the stack)
                 Pop the stack and add the top value to P
              End-While
              Push the latest operator onto the stack     
           End-If  
        End-If
     End-While
     While (the stack is not empty)
        Pop the stack and add the popped value to P
     End-While

Notes:
* At the end, if there is still a left parenthesis at the top of the stack, or if we find a right parenthesis when the stack is empty, then Q contained unbalanced parentheses and is in error.

## Evaluate a postfix expression

Suppose P is an arithmetic expression in postfix notation. We will evaluate it using a stack to hold the operands.

     Start with an empty stack.  We scan P from left to right.

     While (we have not reached the end of P)
        If an operand is found
           push it onto the stack
        End-If
        If an operator is found
           Pop the stack and call the value A
           Pop the stack and call the value B
           Evaluate B op A using the operator just found.
           Push the resulting value onto the stack
        End-If
    End-While
    Pop the stack (this is the final value)
Notes:

* At the end, there should be only one element left on the stack.
* This assumes the postfix expression is valid.

## How can this be implemented?

Work like this is usually done by an assembler, compiler or interpreter. A programmer uses an expression in her or her code, and evaluating it is someone else's problem.

Suppose it is our problem (maybe we are writing an interpreter). The interpreter is reading a line at a time from a file as a string, such as

         A = ((B + C) / 3 - 47 % E) * (F + 8)
The string needs to be parsed--that is, we need to break it up into substrings, each of which is one meaningful part. These substrings are often called tokens. The tokens are separated by spaces, in many cases, but also a token ends if we find a left or right parenthesis or the symbol for an operator. Thus for instance, in the above example, we have "E)", and this consists of two tokens "E" and ")". Bear in mind that the symbol for an operator can be more than one character.

We then have a list of tokens, perhaps in an array or a linked list. Somewhere we will have an Evaluate function which takes such a list as an argument and returns a numeric value.

## A java implementation
```java
/**
 * A perfect calculator
 * <p>
 * Dijkstra’s Two-Stack Algorithm for Expression Evaluation
 * Reference:
 * http://faculty.cs.niu.edu/~hutchins/csci241/eval.htm
 * Discrete mathmatics and its application 7th --> 11.3 Tree Travesal
 *
 * <p>
 * % java Calculator
 * 2 +  ( 3 + 4 ) * ( 5 * 6 )
 * 212.0
 * <p>
 * 1 +  ( 2 + 3 ) * ( 4 * 5 )
 * 101.0
 * <p>
 * ( 1 + sqrt( 5.0+4*3+sqrt(30*2+3) ) ) / 2.0
 * 2.996860725651001
 * <p>
 * ( 1 + sqrt ( 5.0 ) ) / 2.0
 * 1.618033988749895
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class Calculator {
  // key is operator, value is precedence
  private final Map<String, Integer> OPERATOR_MAP = new TreeMap<>();

  public Calculator() {
    // <symbol, precedence>
    OPERATOR_MAP.put("+", 1);
    OPERATOR_MAP.put("-", 1);
    OPERATOR_MAP.put("*", 2);
    OPERATOR_MAP.put("/", 2);
    OPERATOR_MAP.put("^", 3); // power
    OPERATOR_MAP.put("sqrt", 4);
    OPERATOR_MAP.put("sin", 4);
    OPERATOR_MAP.put("cos", 4);
    OPERATOR_MAP.put("log", 4); //natural logarithm

  }

  public double evaluate(String expression) {
    if (expression == null || expression.length() == 0) {
      throw new IllegalArgumentException("empty expression");
    }
    return evaluatePostfix(infixToPostfix(tokenlizedExpression(expression)));
  }

  private List<String> tokenlizedExpression(String expression) {
    List<String> infixQ = new ArrayList<>();
    StringBuilder buf = new StringBuilder();
    int len = expression.length();
    for (int i = 0; i < len; i++) {
      char c = expression.charAt(i);
      if (c == ' ')
        continue;
      if (OPERATOR_MAP.containsKey(String.valueOf(c)) || c == '(' || c == ')') {
        if (buf.length() > 0) {
          infixQ.add(buf.toString());
          buf = new StringBuilder();
        }

        infixQ.add(String.valueOf(c));
      } else
        buf.append(c);
    }

    if (buf.length() > 0)
      infixQ.add(buf.toString());
    return infixQ;

  }

  /**
   * Transform an infix queue to postfix queue
   *
   * @param infixQ
   * @return
   */
  private List<String> infixToPostfix(List<String> infixQ) {
    if (infixQ == null || infixQ.isEmpty()) {
      throw new IllegalArgumentException("empty infixQ");
    }
    List<String> postfixQ = new ArrayList<>();
    //operator stack , a stack in which each item may be a left parenthesis or the symbol for an operation.
    Stack<String> stack = new Stack<String>();
    for (String token : infixQ) {
      //If (an operand is found) Add it to P
      if (isNumeric(token)) {
        postfixQ.add(token);
      } else if (token.equals("(")) {
        stack.push(token);
      } else if (token.equals(")")) {
        while (!stack.isEmpty() && !stack.peek().equals("(")) {
          postfixQ.add(stack.pop());
        }

        //Pop the left parenthesis from the stack and discard it
        if (stack.isEmpty() || !stack.pop().equals("(")) {
          throw new IllegalArgumentException(" then infixQ contained unbalanced parentheses ");
        }
      } else if (OPERATOR_MAP.containsKey(token)) {
        if (stack.isEmpty() || stack.peek().equals("(")) {
          stack.push(token);
        } else {
          while (!stack.isEmpty() && !stack.peek().equals("(") && precedence(token) <= precedence(stack.peek())) {
            postfixQ.add(stack.pop());
          }
          stack.push(token);
        }
      } else {
        throw new IllegalArgumentException(" invalid token found:" + token);
      }
    }
    while (!stack.isEmpty()) {
      String opt = stack.pop();
      if (!opt.equals("("))
        postfixQ.add(opt);
      else
        throw new IllegalArgumentException(" then infixQ contained unbalanced parentheses ");

    }
    return postfixQ;
  }

  /**
   * Evaluate a postfix queue
   *
   * @param postfixQ
   * @return
   */
  private Double evaluatePostfix(List<String> postfixQ) {
    if (postfixQ == null || postfixQ.isEmpty()) {
      throw new IllegalArgumentException("empty postfixQ");
    }
    //operand stack, a stack to hold the operands.
    Stack<Double> stack = new Stack<>();
    for (String token : postfixQ) {
      //If an operand is found push it onto the stack
      if (isNumeric(token)) {
        stack.push(Double.parseDouble(token));
      } else if (OPERATOR_MAP.containsKey(token)) {
        //If an operator is found

        double v = stack.pop();
        if (token.equals("+")) v = stack.pop() + v;
        else if (token.equals("-")) v = stack.pop() - v;
        else if (token.equals("*")) v = stack.pop() * v;
        else if (token.equals("/")) v = stack.pop() / v;
        else if (token.equals("^")) v = Math.pow(stack.pop(), v);
        else if (token.equals("sqrt")) v = Math.sqrt(v);
        else if (token.equals("sin")) v = Math.sin(degreeToRadian(v));
        else if (token.equals("cos")) v = Math.cos(degreeToRadian(v));
        else if (token.equals("log")) v = Math.log(v);
        stack.push(v);
      } else {
        throw new IllegalArgumentException(" invalid token found:" + token);
      }
    }
    //At the end, there should be only one element left on the stack.
    if (stack.size() != 1) {
      throw new IllegalArgumentException("Invalid postfix");
    }
    return stack.pop();
  }

  private int precedence(String token) {
    return OPERATOR_MAP.get(token);
  }

  private static double degreeToRadian(double degree) {
    return degree / 180 * Math.PI;
  }

  private static boolean isNumeric(String token) {
    if (token == null) {
      return false;
    }
    try {
      Double.parseDouble(token);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /**
   * match a number with optional '-' and decimal.
   */
  private static boolean isNumeric2(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  private double debug(String expression) {
    System.out.println("---expression---");
    System.out.println(expression);
    List<String> infixQ = tokenlizedExpression(expression);
    System.out.println("---infix---");
    for (String token : infixQ) {
      System.out.print(token + " ");
    }
    System.out.println("\n---postfix---");
    List<String> postfixQ = infixToPostfix(infixQ);
    for (String token : postfixQ) {
      System.out.print(token + " ");
    }
    System.out.println();
    return evaluatePostfix(postfixQ);
  }

  private static void simulate() {
    //    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //    String line = br.readLine();
    Calculator cal = new Calculator();
    Scanner scanner = new Scanner(System.in, "UTF-8");
    while (scanner.hasNextLine()) {
      String expression = scanner.nextLine().trim();
      if (expression.length() == 0)
        continue;
      if (expression.equals("quit"))
        break;
      System.out.println(cal.evaluate(expression));
    }
  }

  public static void main(String[] args) {

   simulate();
//    test2();
    // testCorrect();
  }

  private static void test1() {

    String expression = "2*(5+4+3+2*2*2+1)";
    Calculator cal = new Calculator();

    System.out.println("value:" + cal.debug(expression));
  }

  private static void test2() {
    List<String> expressions = new ArrayList<>();
    expressions.add("(8+9)^2+(8-4)/3");
    expressions.add("(2+sqrt(5+4))");
    expressions.add("2 + ( 3 + 4 ) * ( 5 * 6 )");
    expressions.add("1 + ( 2 + 3 ) * ( 4 * 5 )");
    expressions.add("(1 + sqrt(5.0+4*3+sqrt(30*2+3)) ) / 2.0");
    expressions.add("(1 + log( 5.0+4*3+sqrt(30*2+3) ) ) / 2.0");
    expressions.add("(1 + sqrt ( 5.0 ) ) / 2.0");
    expressions.add("6*sin(30)");
    expressions.add("6*(5+4+3)");
    expressions.add("2*(5+4+3+2*2*2+1)");
    Calculator cal = new Calculator();
    for (String expression : expressions) {
      System.out.println(expression + " = " + cal.evaluate(expression));
    }
  }

  private static void testCorrect() {
    Calculator cal = new Calculator();
    if (cal.evaluate("(1 + sqrt(5.0+4*3+sqrt(30*2+3)) ) / 2.0") == (1 + Math.sqrt(5.0 + 4 * 3 + Math.sqrt(30 * 2 + 3))) / 2.0) {
      System.out.println("passed");
    } else {
      throw new Error("Not a correct value");
    }
  }
}

```

> http://faculty.cs.niu.edu/~hutchins/csci241/eval.htm
