/**
 * Auto Generated Java Class.
 */
public class ArrayMethodsTest {
    
   private static boolean rowComplete(int[][] sudoku, int rowNum){
      //Create an array which keeps track of which number have occured in a given row
      boolean[] numOccurence = new boolean[this.N];
      for(int i = 0; i <this.n; i++){
        int num = sudoku[rowNum][i];
        //If the value at the specified index is valid and has not occurred prior to this check, update our occurence array
        if(num != 0 && !numOccurrence[i-1]){
         numOccurrence[i-1] = true;
        }
        //If there is a zero entry, or we come across a digit which has previously been seen, the row is not complete
        else{
          return false;
        }
      }
      return true;
    }
  
    private static boolean colComplete(int[][] sudoku, int colNum){
      boolean[] occurences = new boolean[this.N];
      for(int i = 0; i < this.N; i++){
        int contents = sudoku[i][colNum];
        if(contents != 0 && !occurences[i-1]){
          occurences[i-1] = true;
        }
        else{
         return false; 
        }
      }
      return true;
    }
  
  public static void main(String[] args) { 
    
  }
  
  /* ADD YOUR CODE HERE */
  
}
