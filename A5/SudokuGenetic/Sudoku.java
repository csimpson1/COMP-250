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


    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    public void solve()
    {
        /* 
         * First, generate a population of possible solution grids using the given grid as a template.
         * The members of this population (hereafter referred to as chromosomes) will be identical to 
         * the template, but will have all null entries filled in with randomly generated values.
         * All members of this population will be stored into an arraylist
         */ 
     
      //Create an array to hold our population
      int [][][] populationSpace = new int [20][this.N][this.N];
      
      for(int p = 0; p < populationSpace.length; p++){
        for(int i = 0; i< populationSpace[0].length; i++){
          for(int j = 0; j< populationSpace[0][0].length; j++){
            //If the (i,j) index of the Grid is non null, store this value in the 
            if(this.Grid[i][j] != 0){
              populationSpace[p][i][j] = this.Grid[i][j];
            }
            
            else{
              //Generate a random number in the range [1-this.N] and store it in our population space
              int gene = 1+(int)(Math.random() * ((this.N - 1)+1));
              
              //Ensure each column contains the numbers 1-9 exactly once
              while( colCheck(this.Grid, j, gene)){ 
                gene = 1+(int)(Math.random() * ((this.N - 1)+1));
              }
              //Once a unique number is found, store it in the column  
              populationSpace[p][i][j] = 1+(int)(Math.random() * ((this.N - 1)+1));
            }
          }
        }
      }
     
     /*
      *  We next calculate the fitness of each of the members of this population
      */ 
      
    }
    /*
     * Helper method for solve. This method scans a given column of a two dimensional array for a given number.
     * If the number is present, method returns true, false otherwise.
     */ 
    private static boolean colCheck(int[][] a, int colNum, int target){
      for(int i = 0, i < a[0].length; i++){
        if(a[i][colNum] == target){
          return true;//Return true if given number is already present in array
        }
      }
      return false;//Return false if number is not present in array.
    }
    /*
     * Helper method for solve. This method checks the completeness of each row of a grid specified by the parameter 
     * gridNum. Here we are concerned with repeat digits: for every instance of a repeated digit, 1 is added to a counter.
     * If a number does not occur in a grid at all, 10 is added to the counter. This is done to encourage diversity among
     * the numbers in our rows when it comes time to "breed" our solutions.The counter is returned and used in determination of 
     * the fitness of the current grid. A score of 0 means that each row of the grid contains the numbers 1 - N exactly once
     */
    
    private static int colComplete(int [][][] sampleSpace, int gridNum){
      int repeated = 0;
      
      
      for( int rowPtr = 0; rowPtr < sampleSpace[gridNum].length; rowPtr++){
        
        boolean[] hasOccured = new boolean[this.N];//Boolean array to keep track of the occurrence of each digit
        
        for(int colPtr = 0; colPtr < sampleSpace[gridNum][0].length; colPtr++) {
          //If we encounter a repeated digit, update our counter to reflect this
          if(sampleSpace[gridNum][rowPtr][colPtr] == j && hasOccured[j-1]){
            repeated ++;
          }
          //If we encounter a digit for the first time in a row, update our array to reflect its presence 
          else{
            hasOccured[j-1] = true;
          }
        }
        //For every number that does not occur in a row, add ten to our counter
        for(i = 0; i < hasOccured.length, i++){
          if(!hasOccured[i]){
            repeated += 10;
        }
      }  
      return repeated;
    }
      
      private static int cellComplete(int[][][] biosphere, int gridNum){
        int redundants = 0;
        
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
