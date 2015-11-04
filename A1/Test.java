/**
 * Auto Generated Java Class.
 */
import java.io.*;
import java.util.*;

public class Test {
  
 public static int intersectionSizeNestedLoops(int[] L1, int[] L2) {
      int count = 0;
      
      for(int i =0; i < L1.length; i++){
        for(int j = 0; j < L2.length; j++){
          if(L1[i] == L2[j])
            count++; // increment the intersection counter whenever the same student is listed in both classes
        }  
      }  
 return count;
    }
 
 // This method takes as input a sorted array of integers called mySortedArray, the number of elements it contains, and the student ID number to look for
    // It returns true if the array contains an element equal to ID, and false otherwise.
    public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) {
      int l = 0;
      int r = numberOfStudents; //Set r to be one larger than the highest index number in the array (ie the number of elements).
      while(l+1<r){             //This avoids an off by one error
        int midPoint = (l+r)/2;
        
        if(mySortedArray[midPoint] <= ID){
          l = midPoint; //If midpoint is less than ID, search the half of the array with larger indices
        }
        
        else{
          r = midPoint; //If midpoint is greater than or equal to ID, search the half of the array with smaller indices
        }
      }
      
      if(mySortedArray[l] == ID){
        return true;
      }
      else{
       return false;
      }
     }
    
   
    //Method takes as input two studentList objects, returns the number of students present in both studentID arrays.
    //Utilizes the myBinarySearch helper method above.
    public static int intersectionSizeBinarySearch(int[] L1, int[] L2) {
      int count = 0; 
      Arrays.sort(L2);//Sorts the array to be searched using myBinarySearch
      for(int i = 0; i < L1.length; i++){
        if(myBinarySearch(L2, L2.length, L1[i])){
          count++;//If a binary search of L2.studentID for the element L1.studentID at index i returns true, increment count
        }
      }
      return count;
    }
 
     public static int intersectionSizeSortAndParallelPointers(int[] L1, int[] L2) {
      int count = 0;
      Arrays.sort(L1);
      Arrays.sort(L2);
      
      int pL1 = 0;
      int pL2 = 0;
      
      while(pL1 < L1.length && pL2 < L2.length){
        if(L1[pL1] == L2[pL2]){
          count++;//Match found, update intersection count and both pointer values
          pL1++;
          pL2++;
        }
        else if(L1[pL1] < L2[pL2]){
          pL1++;//Search for larger values in L1
        } 
        else{
          pL2++;//Search for larger values in L2
        }
      }
      return count;
    }
     
     //Method takes as input two studentList obejcts and returns the number of elements present in both studentID arrays.
    //To accomplish this, the method merges the two arrays into a single one and sorts this array.It then searches
    //adjacent elements for matches, updating a counter as it finds them.
    public static int intersectionSizeMergeAndSort(int[] L1, int[] L2) {
      int count = 0;
      int[] mergedIDList = new int[L1.length+L2.length];
      
      for(int i = 0; i < L1.length; i++){
        mergedIDList[i] = L1[i];/// Storing elements of the first studentID array into the merged array.
      }
      
      for(int j = L1.length; j < mergedIDList.length; j++){
        mergedIDList[j] = L2[j-L1.length];// Storing elements of the second studentID array into the merged array.
      }
      
      Arrays.sort(mergedIDList);// Sort the elements in the merged list.
      for(int k = 0; k < mergedIDList.length-1; k++){
        if(mergedIDList[k] == mergedIDList[k+1]){
          count++;
          k++;/* If a match is found, increase k.This is because we can assume that we will only encounter pairs of matches,
              //, since no two students will have the same ID number. Increasing k skips to the next unscanned element
              // at index [i+2], which we then compare with the element in index [i+3]. This saves us from doing a comparison
              // which can only ever come back false
              */
        }
      }
      return count;
    }
    
  public static void main (String[] args){
   int[] a = {34,21,33,1,3,5,7,4,2,9};
   int[] b = {121,45,34,7,8,9,1,2,3,5};
   int[] c = {1,2,3,5,7,8,9,34,45,121};
   int intersection;
   boolean GZA;
   
   intersection = intersectionSizeNestedLoops(a,b);
   
   System.out.println("Nested Loops: "+intersection);
   
   intersection = intersectionSizeBinarySearch(a,b);
   System.out.println("Binary Search: "+intersection);
   
   intersection = intersectionSizeSortAndParallelPointers(a,b);
   System.out.println("|| Pointers: "+intersection);
  
   intersection = intersectionSizeMergeAndSort(a,b);
   System.out.println("Merge and Sort: "+intersection);
   
   GZA = myBinarySearch(c, c.length, 121);
   System.out.println(GZA);
 }
}
  
