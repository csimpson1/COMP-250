import java.io.*;
import java.util.*;

public class expressionEvaluator {
    static String delimiters="+-*%()";

    
    /* This method evaluates the given arithmetic expression and returns
     * its Integer value. The method throws an Exception if the expression
     * is malformed.*/
    static Integer evaluate( String expr ) throws Exception {
      
        StringTokenizer st = new StringTokenizer( expr , delimiters , true );    
        Stack<Integer> intArgs = new Stack<Integer>();
        Stack<String> delimiterArgs = new Stack<String>();
        if(!bracketMatch(expr)){
          throw new Exception() ;//Malformed expression given as input, throw exception
        }
        
        /*Fill the integer and delimiter argument stacks with tokens from input string*/
        while (st.hasMoreTokens() ) {
          String token = st.nextToken();//Store current token for sorting
          if(token.equals(")")){
            /*\ Do not add bracket to delimiterArgs, stripping it from expression. Instead, take contents of brackets
             * and perform specified operation on them
            \*/
            Integer product = performOperation(intArgs.pop(), intArgs.pop(), delimiterArgs.pop());
            intArgs.push(product);
          }
          
          else if(token.equals("("));//Do nothing in this case. This strips the bracket from the expression
          
          else if(isInt(token)){ //Store the integer in the stack
            intArgs.push(Integer.parseInt(token));
          }
          
          else{ //Store the operator
            delimiterArgs.push(token);
          }
        }
        /*Evaluate the simplified expression contained in our stacks*/
        while(!(delimiterArgs.empty())){
          Integer product = performOperation(intArgs.pop(), intArgs.pop(), delimiterArgs.pop()); 
          intArgs.push(product);
         
       }
        return intArgs.pop();
    } // end of evaluate 
    
    /*\
     *  Helper method for evaluate. Takes as input the user generated expression, and tests it to make sure that
     * , at least in terms of brackets, it is a well formed expression. Method tests for:
     * ->Bracket matching. Every open bracket must have a closing bracket
     * ->Integer values and arithmatic operators inside brackets. An expression such as 3+() would be considered malformed.
     * Method returns boolean value true if expression is well formed, and false otherwise
    \*/ 
    static boolean bracketMatch(String expression)throws Exception{
      Stack<Character> bracketSum = new Stack<Character>();//This stack will keep track of brackets which are yet to be closed
      for(int i = 0; i < expression.length()-1; i++){
        if(expression.charAt(i) == '('){
          //Check for empty sets of brackets
          if(expression.charAt(i+1) == ')'){
            return false;
          }
          Character openBrac = new Character(expression.charAt(i));
          bracketSum.push(openBrac);//Add open bracket to total
        }
        else if(expression.charAt(i) == ')'){
          /*\ Two cases covered here. Either we have a closing bracket where there should not be one, in which case
           *  an EmptyStackException is thrown, or else we have closed an open bracket, in which case we
           *  remove a bracket from bracketSun
          \*/
          bracketSum.pop();
        }
      }
      //Check last character in expression.
      if(expression.charAt(expression.length()-1) == '(')
        return false;
      else if(expression.charAt(expression.length()-1) == ')'){
        bracketSum.pop();
      }
      //Check to see if all brackets closed
      else if(!bracketSum.empty()){
        return false;
      }
      return true; 
    }
      
    
    
    /*\ 
     * Helper method for evaluate. Tests a token to see if it is an integer, returns boolean value to this effect.
     * Used in sorting tokens into integer and delimiter arguments
    \*/
    static boolean isInt(String token){
      try{
        Integer.parseInt(token);
        return true;
      }
      catch(NumberFormatException e){
        return false;
      }
    }
    
    /*\
     * Helper method for evaluate. Performs arithmatical operation defined by operand on the two Integer parameters
     * 
    \*/
    static Integer performOperation(Integer a, Integer b, String op){
      Integer opProduct;
        if(op.equals("+")){
          opProduct = b + a;
        }
        else if(op.equals("-")){
          opProduct = b - a;
        }
        else if(op.equals("*")){
          opProduct = b * a;  
        }
        else if(op.equals("%")){
          opProduct = b / a;
        }
        else{
        opProduct = null;
        }
        return opProduct;
    }
 
    /* This method repeatedly asks the user for an expression and evaluates it.
       The loop stops when the user enters an empty expression */
    public void queryAndEvaluate() throws Exception {    
        String line;
        BufferedReader stdin = new BufferedReader(new InputStreamReader( System.in ) );
         System.out.println("Enter an expression");
        line = stdin.readLine();    
    
        while ( line.length() > 0 ) {
            try {
                Integer value = evaluate( line );
                System.out.println("value = " + value );
            }
            catch (Exception e)
            {
                System.out.println("Malformed Expression.");
            }
            System.out.println( "Enter an expression" );
            line = stdin.readLine();    
        } // end of while loop
    } // end of query and evaluate
        
    public static void main(String args[]) throws Exception {
         expressionEvaluator e=new expressionEvaluator();
         e.queryAndEvaluate();
     } // end of main
}

// 2013