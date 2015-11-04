/**
 * Auto Generated Java Class.
 */
public class ArrayMethodsTest {
  /*
   * All good. Ready to implement changes in main code
   */ 
  
   private static boolean rowComplete(int[][] sudoku, int rowNum){
      //Create an array which keeps track of which number have occured in a given row
      boolean[] numOccurence = new boolean[sudoku.length];//this.N reference
      for(int i = 0; i <sudoku.length; i++){//this.N reference
        int num = sudoku[rowNum][i];
        //If the value at the specified index is valid and has not occurred prior to this check, update our occurence array
        if(num != 0 && !numOccurence[num-1]){ //changed i to num
         numOccurence[num - 1] = true; //changed i to num
        }
        //If there is a zero entry, or we come across a digit which has previously been seen, the row is not complete
        else{
          return false;
        }
      }
      return true;
    }
  /*
   * All good. Ready to implement changes in main code 
   */
    private static boolean colComplete(int[][] sudoku, int colNum){
      boolean[] occurences = new boolean[sudoku.length];//this.N refference
      for(int i = 0; i < sudoku.length; i++){//this.N refference
        int contents = sudoku[i][colNum];
        if(contents != 0 && !occurences[contents-1]){ //changed i to contents
          occurences[contents-1] = true;//changed i to contents
        }
        else{
         return false; 
        }
      }
      return true;
    }
  /*
   * All good. Ready to implement in main code
   */ 
    private static boolean boxComplete(int[][] game, int topLeftX, int topLeftY){
      boolean[] occurences = new boolean[game.length];//this.N ref
      int j;//Declared outside so as to allow us to reset it outside of the inner loop
      for(int i = topLeftX; i < topLeftX + 3; i++){ //3 here is a this.SIZE ref
        for( j = topLeftY; j < topLeftY + 3; j++){//3 here is a this.SIZE ref
          int subject = game[i][j];
          //If we find the first occurence of a valid number, update the array to reflect this
          if(subject != 0 && !occurences[subject - 1]){
           occurences[subject - 1] = true; 
          }
          //If we find an invalid number(a 0 or a repeated number) return false
          else{
           return false; 
          }
        }
        j = topLeftY; //reset our column pointer
      }
     return true;
    }
    //Good to implement in main code
    private static void rowPosibilities(int[][] gameSpace, boolean[][][] possibles, int row){
      for(int i = 0; i < gameSpace.length; i++){
        //If we find a number in a row, remove this number from the set of possible values for all other slots in the given row.
        if(gameSpace[row][i] != 0){
          int num = gameSpace[row][i];
          for(int j = 0; j < possibles.length; j++){
            possibles[row][j][num - 1] = false;
          }
        }
      }
    }
    //Good to implement in main code
    private static void colPossibilities(int[][] gameGrid, boolean[][][] validChoices, int col){
      for (int i = 0; i < gameGrid.length; i++){
       //If we find a number in a column, remove this number from the set of possible values for all other slots in the given row
        if(gameGrid[i][col] != 0){
          int number = gameGrid[i][col];
          for(int j = 0; j < validChoices.length; j++){
            validChoices[j][col][number - 1] = false;
          }
        }
      }
    }
      
    
    private static void cellPossibilities(int [][] sudokuGrid, boolean[][][] candidates, int topLeftX, int topLeftY){
     int j, l;
     for(int i = topLeftX; i < topLeftX + 3; i ++){//this.SIZE ref
       for(j = topLeftY; j < topLeftY + 3; j++){//this.SIZE ref
         
         //If we find a valid number in a cell, remove this number from the set of all possible values for all other slots in the given cell
         if(sudokuGrid[i][j] != 0){
           int toRemove = sudokuGrid[i][j];
           for(int k = topLeftX; k < topLeftX + 3; k++){//this.SIZE ref
             for( l = topLeftY; l < topLeftY + 3; l++){//this.SIZE ref
               candidates[k][l][toRemove - 1] = false;
             }
           }
           l = topLeftY;//reset our candidates column pointer
         }
       }
       j = topLeftY;//reset our sudokuGrid column pointer
     }
    }
    //Good to implement in main code
    private static void fillTrue(int[][] sudokuGrid, boolean[][][] array){
      for(int i  = 0; i < sudokuGrid.length; i++){
        for(int j = 0; j < sudokuGrid[0].length; j++){
          //If a particular slot is empty, initialize all values of the possibility array corrensponing to that index to true
          if(sudokuGrid[i][j] == 0){
            for(int k = 0; k < array[0][0].length; k ++){
              array[i][j][k] = true;
            }
          }  
        }
      }
    }
    
  public static void main(String[] args) { 
    int[][] testGrid = new int[9][9];
    
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        if( i == 0 ){
          testGrid[i][j] = j + 1;
        }
        
        if( j == 0 && i > 0){
          testGrid[i][j] = i + 1;
        }
        
      }
    }
    
    testGrid[6][6] = 1;
    testGrid[6][7] = 2;
    testGrid[6][8] = 3;
    testGrid[7][6] = 4;
    testGrid[7][7] = 5;
    testGrid[7][8] = 6;
    testGrid[8][6] = 7;
    testGrid[8][7] = 8;
    testGrid[8][8] = 0;
    
    int[][] test2 = new int[9][9];
    boolean[][][] test2Possibilities = new boolean[9][9][9];
    
    //Row test
    for(int i = 0; i < 9; i ++){
      if(i != 8){
        test2[0][i] = i + 1;
      }
      else{
       test2[0][i] = 0; 
      }
    }
    //ColumnTest
    for(int j = 0; j < 9; j ++){
      if(j != 2){
        test2[j][0] = j + 1;
      }
    }
    fillTrue(testGrid,test2Possibilities);
    cellPossibilities(testGrid, test2Possibilities, 6, 6);
  }
}
