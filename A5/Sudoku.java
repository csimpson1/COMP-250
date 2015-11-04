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
      //Create an array which represents vaild numbers that each cell can store
      boolean [][][] validityMatrix = new boolean[this.N][this.N][this.N];
      //
      
    }
    /*
     * Helper method for solve.This method acts on the sudoku grid. Tests to see if a target value lies in a specified row of
     * a two dimensional array. Method then returns a boolean value representing if the target 
     * value is present in the given row
     */
    private static boolean checkRow(int[][] gameSpace, int rowNum, int target){
      for(int i = 0; i < gameSpace.length; i++){
        if(gameSpace[rowNum][i] == target){
          return true;
        }
      }
      return false;
    }
    /*
     * Helper method for solve.This method acts on the sudoku grid and tests to see if a target value lies in a specified column of a 
     * two dimensional array. Method then returns a boolean value to this effect.
     */ 
    
    private static boolean checkColumn( int[][] gameSpace, int colNum, int targetVal){
      for(int i = 0; i < gameSpace.length; i++){
        if(gameSpace[i][colNum] == targetVal){
          return true;
        }
      }
      return false;
    }
    /*
     * Helper method for solve.This method acts on the sudoku grid. Tests to see if a target value is in a given box 
     * on the grid.Method returns a boolean value to this effect. This method assumes all parameter arrays are square.
     */ 
    private static boolean checkBox( int[][] gameSpace, int exRow, int exCol, int searchVal){
      int boxSize = Math.sqrt(gameSpace.length);//Find the box size for this particular grid
      
      //Scan box for search value, excluding the cell with coordinates 
      for(int i = exRow % boxSize; i < exRow + boxSize - exRow % boxSize; i++){
        for(int j  = exCol % boxSize; j < exCol + boxSize - exRow % boxSize; j++){
          if(i != exRow && j != exCol){
            if(gameSpace[i][j] == searchVal){
              return true;
            }
          }
        }
      }
      return false;
    }
    
    /*
     * Helper method for solve. This method acts on the possibilities matrix. If the index of a boolean array in
     * the parameter array corresponds to an empty cell on the sudoku grid, it sets all values at this index to true 
     * This method assumes both input arrays are square.
     */ 
    
    public static void setEmptyTrue(boolean[][][] a, int[][] grid){
      for(int i = 0; i < a.length; i++){
        for(int j = 0; j < a.length; j++){
          if(grid[i][j] == 0){
            Arrays.fill(a[i][j], true);
          }
        }
      }
    }
    
    /*
     * Helper method for solve. This method works on the possibility array. If a target value is present in a specified
     * row, this method goes through the specified row, and sets the possibility values of all cells not currently 
     * storing a number to reflect the fact that the targer value is no longer a valid choice of number for that cell
     *This method assumes the parameter array is square
     */ 
    
    public static void updateRow(boolean[][][] possibilities, int[][] grid, int targetValue, int rowNum){
      for(int i = 0; i < possibilities.length; i++){
        if(grid[rowNum][i] == 0){
          possibilities[rowNum][i][targetVal -1] = false;
        }
          
      }
    }
    
    public static void updateColumn(boolean[][][] possibilities, int[][] sudoku, int searchVal, int colNum){
      for(int i = 0; i < possibilities.length; i++){
        if(sudoku[i][colNum] == 0){
          posibilities[i][colNum][targetVal-1] = false;
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
