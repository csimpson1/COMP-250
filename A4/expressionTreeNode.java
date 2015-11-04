import java.lang.Math.*;

class expressionTreeNode {
    private Object value;
    private expressionTreeNode leftChild, rightChild, parent;
    
    expressionTreeNode() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  expressionTreeNode l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created expressionTreeNode               
    */
    expressionTreeNode(String s, expressionTreeNode l, expressionTreeNode r, expressionTreeNode p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    Object getValue() { return value; }

    expressionTreeNode getLeftChild() { return leftChild; }

    expressionTreeNode getRightChild() { return rightChild; }

    expressionTreeNode getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(Object o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(expressionTreeNode n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(expressionTreeNode n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    expressionTreeNode(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new expressionTreeNode(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.07
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new expressionTreeNode( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2013
    expressionTreeNode deepCopy() {
        expressionTreeNode n = new expressionTreeNode();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = (String) value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 


    // Returns the value of the the expression rooted at a given node
    // when x has a certain value   
    
    double evaluate(double x){
     expressionTreeNode workingTree = this.deepCopy();
     workingTree.substituteVarValue(x);
     return workingTree.evaluateTree();
    } 
    /*
     * Helper for evaluate. Evaluates the deep copy of the original tree that has had all instances of "x" replaced with
     * the user defined variable value
     */ 
    double evaluateTree(){
      double exprVal;
      if(this.isLeaf()){
        
        try{exprVal = Double.parseDouble(this.getValue().toString());}
        catch(NumberFormatException e){
          System.out.println("Someone done goofed");
          exprVal = 0;
        }
      }
      else{
        String operator, operand1, operand2;
        operator = this.getValue().toString();
        
        //Base case. We are either dealing with a node with two leafs as progeny, or a node with exactly one child, which is a leaf 
        if(this.getLeftChild().isLeaf() && (this.getRightChild() == null || this.getRightChild().isLeaf())){
          operand1 = this.getLeftChild().getValue().toString();
       
          try{operand2 = this.getRightChild().getValue().toString();}
          catch(NullPointerException e){ operand2 = null;}
        }
     
        else if(this.getLeftChild().isLeaf() && this.getRightChild() != null){ 
          operand1 = this.getLeftChild().getValue().toString();
          operand2 = Double.toString(this.getRightChild().evaluateTree());
        }
     
        else if(this.getLeftChild() != null && this.getRightChild().isLeaf()){
         operand1 = Double.toString(this.getLeftChild().evaluateTree());
         operand2 = this.getRightChild().getValue().toString();
        }
        
        else{
         operand1 = Double.toString(this.getLeftChild().evaluateTree());
         operand2 = Double.toString(this.getRightChild().evaluateTree());
        }
        
        exprVal = performOperation(operator, operand1, operand2);
      }
      return exprVal;
    }
    /*
     * Helper for evaluate method. Performs the specified unary operations(sin, cos, exp, add, minus, mult)
     * present in the expressionTreeNode.To allow for performing of both unary and binary operations the operand1 
     * argument is reserved for non-null values, and all of the unary operands (sin, cos, exp) will use this
     * argument exclusively
     */
    static double performOperation(String operator, String operand1, String operand2){
      double opValue1 = Double.parseDouble(operand1);
      double opValue2 = 0;
      if(operand2 != null){opValue2 = Double.parseDouble(operand2);}
      
      if(operator.equals("sin")){return Math.sin(opValue1);}
      
      else if(operator.equals("cos")){return Math.cos(opValue1);}
      
      else if(operator.equals("exp")){return Math.exp(opValue1);}
      
      else if(operator.equals("add")){return opValue1 + opValue2;}
      
      else if(operator.equals("minus")){return opValue1 - opValue2;}
      
      else{return opValue1 * opValue2;}
    }
    
    /*
     * Helper for evaluate and subsituteVarValue methods. Tests to see if a given node is a leaf, returns a boolean value to this effect
     */
    boolean isLeaf(){
      if(this.getLeftChild() == null && this.getRightChild() == null)
        return true;
      else
        return false;
    }
   /*
    * Helper for substituteVarValue method. Tests to see if the value of a given node is a variable, returns a boolean to this
    * effect
    */ 
    boolean containsVar(){
      if(this.getValue().equals("x"))
        return true;
      else
        return false;
    }
    
    /*
     * Helper for the evaluateMethod. Replaces all instances of the variable x in the expressionTree with the value 
     * entered by the user
     */ 
    void substituteVarValue(double userInput){
      if(this.isLeaf()){
        if(this.getValue().equals("x")){
          String substitution = String.valueOf(userInput);
          this.setValue(substitution);
        }
      }
      else{
        this.getLeftChild().substituteVarValue(userInput);
        if(this.getRightChild() != null){
          this.getRightChild().substituteVarValue(userInput);
        }
      }
    }
    /* returns the root of a new expression tree representing the derivative of the
    expression represented by the tree rooted at the node on which it is called ***/
    expressionTreeNode differentiate() {
      expressionTreeNode derivative = this.deepCopy();
      derivative.differentiateTree();
      return derivative; 
    }
    
    /*
     * Helper for differentiate method. Takes the derivative of a copy of the user defined tree
     */ 
    expressionTreeNode differentiateTree(){
      if(!this.isLeaf()){
        //Rules for addition and subtraction
        if(this.getValue().equals("add") || this.getValue().equals("minus")){
          String lDerivative = this.getLeftChild().differentiateTree().toString();
          String rDerivative = this.getRightChild().differentiateTree().toString();
          expressionTreeNode leftDer = new expressionTreeNode(lDerivative);
          expressionTreeNode rightDer = new expressionTreeNode(rDerivative);
          this.setLeftChild(leftDer);
          this.setRightChild(rightDer);
        }
        
        //product rule
        else if(this.getValue().equals("mult")){
          String lExp = this.getLeftChild().toString();
          String rExp = this.getRightChild().toString();
          String lDerivative = this.getLeftChild().differentiateTree().toString();
          String rDerivative = this.getRightChild().differentiateTree().toString();
          
          expressionTreeNode leftProduct = new expressionTreeNode("mult");
          expressionTreeNode rightProduct = new expressionTreeNode("mult");
          expressionTreeNode leftDer = new expressionTreeNode(lDerivative);
          expressionTreeNode rightDer = new expressionTreeNode(rDerivative);
          expressionTreeNode leftExp = new expressionTreeNode(lExp);
          expressionTreeNode rightExp = new expressionTreeNode(rExp);
          
          leftProduct.setLeftChild(leftDer);
          leftProduct.setRightChild(leftExp);
          
          rightProduct.setLeftChild(rightDer);
          rightProduct.setRightChild(rightExp);
          
          this.setLeftChild(leftProduct);
          this.setRightChild(rightProduct);
          this.setValue("add");
        }
        
        //rule for exponential function
        else if(this.getValue().equals("exp")){
          String inExp = this.getLeftChild().toString();
          String inDerivative = this.getLeftChild().differentiateTree().toString();
          
          expressionTreeNode eTerm = new expressionTreeNode("exp");
          expressionTreeNode innerDer = new expressionTreeNode(inDerivative);
          expressionTreeNode innerExp = new expressionTreeNode(inExp);
          
          eTerm.setLeftChild(innerExp);
          
          this.setLeftChild(eTerm);
          this.setRightChild(innerDer);
          this.setValue("mult");
        }
        //Sise and cosine derivative rules
        else if(this.getValue().equals("sin")){
          String inExp = this.getLeftChild().toString();
          String inDerivative = this.getLeftChild().differentiateTree().toString();
          
          expressionTreeNode innerExp = new expressionTreeNode(inExp);
          expressionTreeNode innerDer = new expressionTreeNode(inDerivative);
          expressionTreeNode cosTerm = new expressionTreeNode("cos");
          
          cosTerm.setLeftChild(innerExp);
          
          this.setLeftChild(cosTerm);
          this.setRightChild(innerDer);
          this.setValue("mult");
        }
        
        else{
          String inExp = this.getLeftChild().toString();
          String inDerivative = this.getLeftChild().differentiateTree().toString();
          
          expressionTreeNode negativeSign = new expressionTreeNode("minus(0,1)");
          expressionTreeNode sinTerm = new expressionTreeNode("sin");
          expressionTreeNode multTerm = new expressionTreeNode("mult");  
          expressionTreeNode innerExp = new expressionTreeNode(inExp);
          expressionTreeNode innerDer = new expressionTreeNode(inDerivative);
          
          sinTerm.setLeftChild(innerExp);
          
          multTerm.setLeftChild(sinTerm);
          multTerm.setRightChild(innerDer);
          
          this.setLeftChild(negativeSign);
          this.setRightChild(multTerm);
          this.setValue("mult");
        }
      }
      //Base cases for recursion. Derivatives of variables and constants
      else{
        if(this.getValue().equals("x")){
          this.setValue("1");
        }
        else{
          this.setValue("0");
        }
      }
      return this;
    }
    
    
    
    public static void main(String args[]) {
        expressionTreeNode e = new expressionTreeNode("mult(add(2,x),cos(x))");
 
        System.out.println(e);
  
        System.out.println(e.differentiate());
 
    }
   }