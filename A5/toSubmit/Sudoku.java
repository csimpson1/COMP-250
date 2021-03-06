import java.util.*;
import java.io.*;



class Sudoku
{
    /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
     * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
    int SIZE, N;

    /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
     * not yet been revealed are stored as 0. */
    int Grid[][];
    
    /*
     * This array keeps record of all possible valid values for every square on the board. 
     * This array is declared here due to the neccessity of it being availiable at every level 
     * of the recursive part of the solve funtion
     */ 
    boolean possibilitiesMatrix[][][];


    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    public void solve(){
      //Find and mark all constant members of the grid
      this.setConstants();
      //Fill the possibility array corresponding to each empty slot on the grid with true values 
      this.fillPMatrixTrue();
      //Fill in possibility array with respect to starting configuration of grid
      this.updatePMatrix();
      try{ 
        this.recursiveSolve(0,0);
      }
      
      catch(Exception e){
      }
    }
    

    /*---------------------------------------------------------------------------------------------------------*
     *                                     Posibility Matrix Methods                                           *
     *---------------------------------------------------------------------------------------------------------*/ 
    
    /*
     * Helper Method for fillPMatrixTrue. Marks any grid constant as such (here a grid constant is 
     * a number in the grid that was passed at the command line), by changing the 0th index of the possibilityMatrix
     * to true
     */
     
    private void setConstants(){
      for(int i = 0; i < this.N; i++){
        for(int j = 0; j < this.N; j++){
          if(this.Grid[i][j] != 0){
            this.possibilitiesMatrix[i][j][0] = true; 
          }
        }
      }
    }
    
    /*
     * Helper method for solve. This method initializes the values of the possibility array for each empty slot(in our case
     * represented by containing a zero value) to true
     */ 
    
     private void fillPMatrixTrue(){
      for(int i  = 0; i < this.N; i++){
        for(int j = 0; j < this.N; j++){
          //If a particular slot is empty, initialize all values of the possibility array corrensponing to that index to true
          if(!this.possibilitiesMatrix[i][j][0]){
            this.fillPMatrixIndexTrue(i, j);
          }
        }
      }
    }
     
     /*
      * Helper method for recursiveSolve, fillPMatrixTrue. Method sets the boolean values
      * of a given slot of the possibilites matrix to true
      */ 
     private void fillPMatrixIndexTrue(int row, int col){
       for(int k = 1; k < this.N+1; k ++){
         this.possibilitiesMatrix[row][col][k] = true;
       }
     }
    
    /*
     * A helper method for solve. This method scans a given row the sudoku grid for empty slots and computes the possible
     * valid numbers which can be stored in them. These possibilities are stored in the 3D boolean array passed as a parameter.
     */ 
    
    private void rowPossibilities(int row){
      for(int i = 0; i < this.N; i++){
        //If we find a number in a row, remove this number from the set of possible values for all other slots in the given row.
        if((!this.possibilitiesMatrix[row][i][0]) && (this.Grid[row][i] == 0)){
          int num = this.Grid[row][i];
          for(int j = 0; j < this.N; j++){
            this.possibilitiesMatrix[row][j][num] = false;
          }
        }
      }
    }
    /*
     * Helper method for solve. This method scans a given column for empty slots and computes the possible values that they can hold. These possibilties
     * are then stored in the 3D boolean array passed to the method.
     */ 
     private void colPossibilities(int col){
      for (int i = 0; i < this.N; i++){
       //If we find a number in a column, remove this number from the set of possible values for all other slots in the given row
        if((!this.possibilitiesMatrix[i][col][0]) && (this.Grid[i][col] == 0)){
          int number = this.Grid[i][col];
          for(int j = 0; j < this.N; j++){
            this.possibilitiesMatrix[j][col][number] = false;
          }
        }
      }
    }
    
    /*
     * Helper method for solve. Like the previous two methods, this one calcualtes the possible values for some subset of the gird,
     * in this case a given cell specified by the X and Y coordinates of its top left corner.
     */
     
    private void cellPossibilities(int topLeftX, int topLeftY){
     int j, l;
     for(int i = topLeftX; i < topLeftX + this.SIZE; i ++){
       for(j = topLeftY; j < topLeftY + this.SIZE; j++){
         
         //If we find a valid number in a cell, remove this number from the set of all possible values for all other slots in the given cell
         if((!this.possibilitiesMatrix[i][j][0]) && (this.Grid[i][j] == 0)){
           int toRemove = this.Grid[i][j];
           for(int k = topLeftX; k < topLeftX + this.SIZE; k++){
             for( l = topLeftY; l < topLeftY + this.SIZE; l++){
               this.possibilitiesMatrix[k][l][toRemove] = false;
             }
           }
           l = topLeftY;//reset our candidates column pointer
         }
       }
       j = topLeftY;//reset our sudokuGrid column pointer
     }
    }
    
    /*
     * Helper method for solve, recursiveSolve. Method updates the possibilites matrix to reflect
     * current board configuration
     */ 
    private void updatePMatrix(){
      //Scan rows for possible values
      for(int i = 0; i < this.N; i++){
        this.rowPossibilities(i);
      }
      //Scan columns for possible values
      for(int j = 0; j < this.N; j++){
        this.colPossibilities(j);
      }
      //Scan  cells for possible values
      for(int k = 0; k < this.N; k+=this.SIZE){
        for(int l = 0; l < this.N; l += this.SIZE){
          this.cellPossibilities(k,l);
        }  
      }
    }
    /*---------------------------------------------------------------------------*
     *                  Methods used for recursive part of Solve                 *
     *                                                                           *
     *---------------------------------------------------------------------------*/
    private void recursiveSolve(int xCoord, int yCoord) throws Exception{
      if(xCoord > this.N-1){
        throw new Exception();
      }
      
      if(!this.possibilitiesMatrix[xCoord][yCoord][0]){
        this.updatePMatrix();
        for(int a = 1; a < this.N+1; a++){
          if(this.possibilitiesMatrix[xCoord][yCoord][a]){
           this.Grid[xCoord][yCoord] = a;
           if (yCoord >= this.N-1){
             recursiveSolve(xCoord + 1, 0); 
             
           }
           else{
             recursiveSolve(xCoord, yCoord + 1);
           }
          }
        }
      this.Grid[xCoord][yCoord] = 0;
      this.fillPMatrixTrue();
      this.updatePMatrix();
      }
      
      else{
        if (yCoord >= this.N-1){
          recursiveSolve(xCoord + 1, 0);
        }
        else{
          recursiveSolve(xCoord, yCoord + 1);
        }
      }
    }
        
    /*****************************************************************************/
    /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
    /*****************************************************************************/
 
    /* Default constructor.  This will initialize all positions to the default 0
     * value.  Use the read() function to load the Sudoku puzzle from a file or
     * the standard input. */
    public Sudoku( int size )
    {
        SIZE = size;
        N = size*size;

        Grid = new int[N][N];
        for( int i = 0; i < N; i++ ) 
            for( int j = 0; j < N; j++ ) 
                Grid[i][j] = 0;
        possibilitiesMatrix = new boolean[N][N][N+1];
    }


    /* readInteger is a helper function for the reading of the input file.  It reads
     * words until it finds one that represents an integer. For convenience, it will also
     * recognize the string "x" as equivalent to "0". */
    static int readInteger( InputStream in ) throws Exception
    {
        int result = 0;
        boolean success = false;

        while( !success ) {
            String word = readWord( in );

            try {
                result = Integer.parseInt( word );
                success = true;
            } catch( Exception e ) {
                // Convert 'x' words into 0's
                if( word.compareTo("x") == 0 ) {
                    result = 0;
                    success = true;
                }
                // Ignore all other words that are not integers
            }
        }

        return result;
    }


    /* readWord is a helper function that reads a word separated by white space. */
    static String readWord( InputStream in ) throws Exception
    {
        StringBuffer result = new StringBuffer();
        int currentChar = in.read();
        String whiteSpace = " \t\n";

        // Ignore any leading white space
        while( whiteSpace.indexOf(currentChar) > -1 ) {
            currentChar = in.read();
        }

        // Read all characters until you reach white space
        while( whiteSpace.indexOf(currentChar) == -1 ) {
            result.append( (char) currentChar );
            currentChar = in.read();
        }
        return result.toString();
    }


    /* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
     * grid is filled in one row at at time, from left to right.  All non-valid
     * characters are ignored by this function and may be used in the Sudoku file
     * to increase its legibility. */
    public void read( InputStream in ) throws Exception
    {
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = readInteger( in );
            }
        }
    }


    /* Helper function for the printing of Sudoku puzzle.  This function will print
     * out text, preceded by enough ' ' characters to make sure that the printint out
     * takes at least width characters.  */
    void printFixedWidth( String text, int width )
    {
        for( int i = 0; i < width - text.length(); i++ )
            System.out.print( " " );
        System.out.print( text );
    }


    /* The print() function outputs the Sudoku grid to the standard output, using
     * a bit of extra formatting to make the result clearly readable. */
    public void print()
    {
        // Compute the number of digits necessary to print out each number in the Sudoku puzzle
        int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

        // Create a dashed line to separate the boxes 
        int lineLength = (digits + 1) * N + 2 * SIZE - 3;
        StringBuffer line = new StringBuffer();
        for( int lineInit = 0; lineInit < lineLength; lineInit++ )
            line.append('-');

        // Go through the Grid, printing out its values separated by spaces
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                printFixedWidth( String.valueOf( Grid[i][j] ), digits );
                // Print the vertical lines between boxes 
                if( (j < N-1) && ((j+1) % SIZE == 0) )
                    System.out.print( " |" );
                System.out.print( " " );
            }
            System.out.println();

            // Print the horizontal line between boxes
            if( (i < N-1) && ((i+1) % SIZE == 0) )
                System.out.println( line.toString() );
        }
    }


    /* The main function reads in a Sudoku puzzle from the standard input, 
     * unless a file name is provided as a run-time argument, in which case the
     * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
     * outputs the completed puzzle to the standard output. */
    public static void main( String args[] ) throws Exception
    {
        InputStream in;
        if( args.length > 0 ) 
            in = new FileInputStream( args[0] );
        else
            in = System.in;

        // The first number in all Sudoku files must represent the size of the puzzle.  See
        // the example files for the file format.
        int puzzleSize = readInteger( in );
        if( puzzleSize > 100 || puzzleSize < 1 ) {
            System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
            System.exit(-1);
        }

        Sudoku s = new Sudoku( puzzleSize );

        // read the rest of the Sudoku puzzle
        s.read( in );

        // Solve the puzzle.  We don't currently check to verify that the puzzle can be
        // successfully completed.  You may add that check if you want to, but it is not
        // necessary.
        s.solve();

        // Print out the (hopefully completed!) puzzle
        s.print();
    }
}
