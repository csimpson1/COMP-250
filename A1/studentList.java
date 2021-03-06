//STUDENT1 NAME: Colby Simpson
//STUDENT1 ID: 260454529

import java.io.*;    
import java.util.*;

class studentList {
    int studentID[];
    int numberOfStudents;
    String courseName;
    
    // A constructor that reads a studentList from the given fileName and assigns it the given courseName
    public studentList(String fileName, String course) {
 String line;
 int tempID[]=new int[4000000]; // this will only work if the number of students is less than 4000000.
 numberOfStudents=0;
 courseName=course;
 BufferedReader myFile;
 try {
     myFile = new BufferedReader(new FileReader( fileName ) ); 

     while ( (line=myFile.readLine())!=null ) {
  tempID[numberOfStudents]=Integer.parseInt(line);
  numberOfStudents++;
     }
     studentID=new int[numberOfStudents];
     for (int i=0;i<numberOfStudents;i++) {
  studentID[i]=tempID[i];
     }
 } catch (Exception e) {System.out.println("Can't find file "+fileName);}
 
    }
    

    // A constructor that generates a random student list of the given size and assigns it the given courseName
    public studentList(int size, String course) {
 int IDrange=2*size;
 studentID=new int[size];
 boolean[] usedID=new boolean[IDrange];
 for (int i=0;i<IDrange;i++) usedID[i]=false;
 for (int i=0;i<size;i++) {
     int t;
     do {
  t=(int)(Math.random()*IDrange);
     } while (usedID[t]);
     usedID[t]=true;
     studentID[i]=t;
 }
 courseName=course;
 numberOfStudents=size;
    }
    
    // Returns the number of students present in both lists L1 and L2
    public static int intersectionSizeNestedLoops(studentList L1, studentList L2) {
      int count = 0;
      
      for(int i =0; i < L1.studentID.length; i++){
        for(int j = 0; j < L2.studentID.length; j++){
          if(L1.studentID[i] == L2.studentID[j])
            count++; // increment the intersection counter whenever the same student is listed in both classes
        }  
      }  
 return count;
    }
    
    
    // This method takes as input a sorted array of integers called mySortedArray, the number of elements it contains, and the student ID number to look for
    // It returns true if the array contains an element equal to ID, and false otherwise.
    public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) {
      int l = 0;
      int r = numberOfStudents; //Set r to be one more than the value of the largest index in mySortedArray.
      while(l+1<r){             //This avoids an off by one error.
        int midPoint = (l+r)/2;
        
        if(mySortedArray[midPoint] < ID){
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
    public static int intersectionSizeBinarySearch(studentList L1, studentList L2) {
      int count = 0; 
      Arrays.sort(L2.studentID);//Sorts the array to be searched using myBinarySearch
      for(int i = 0; i < L1.studentID.length; i++){
        if(myBinarySearch(L2.studentID, L2.numberOfStudents, L1.studentID[i])){
          count++;//If a binary search of L2.studentID for the element L1.studentID at index i returns true, increment count
        }
      }
      return count;
    }
    //Method takes as input two studentList objects, and returns number of elements present in both studentID arrays.
    //Method accomplishes this task using two pointer values to travel through studentID arrays of L1 and L2
    public static int intersectionSizeSortAndParallelPointers(studentList L1, studentList L2) {
      int count = 0;
      Arrays.sort(L1.studentID);
      Arrays.sort(L2.studentID);
      
      int pL1 = 0;
      int pL2 = 0;
      
      while(pL1 < L1.studentID.length && pL2 < L2.studentID.length){
        if(L1.studentID[pL1] == L2.studentID[pL2]){
          count++;//Match found, update intersection count and both pointer values
          pL1++;
          pL2++;
        }
        else if(L1.studentID[pL1] < L2.studentID[pL2]){
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
    public static int intersectionSizeMergeAndSort(studentList L1, studentList L2) {
      int count = 0;
      int[] mergedIDList = new int[L1.studentID.length+L2.studentID.length];
      
      for(int i = 0; i < L1.studentID.length; i++){
        mergedIDList[i] = L1.studentID[i];/// Storing elements of the first studentID array into the merged array.
      }
      
      for(int j = L1.studentID.length; j < mergedIDList.length; j++){
        mergedIDList[j] = L2.studentID[j-L1.studentID.length];// Storing elements of the second studentID array into the merged array.
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
    
    
    
   
 
    public static void main(String[] args){
      
      //Iterator for list size. Will create lists of size 2^1-2^9
      //for(int i = 10; i<=19; i++){  
      //  int size = (int)(500*Math.pow(2,i-9));
      //  System.out.println("Size: "+ size);
       
        long listGenStartTime = System.currentTimeMillis();
        for(int j = 1; j <= 100; j++){
          studentList methodMan = new studentList(64000, "COMP250 - Introduction to Computer Science");
          studentList  raekwonTheChef = new studentList(32000, "MATH240 - Discrete Mathematics");
        }
        long listGenEndTime = System.currentTimeMillis();
        long tigerStyle = (listGenEndTime - listGenStartTime)/1000;//Calculate the time it takes to generate a list of size i
        System.out.println("List sizes 64000, 32000 avg gen time: "+ tigerStyle);

        long shaolinShadowBoxing = System.currentTimeMillis();
        
        for(int rep = 1; rep <= 50; rep++){ //Iterator for number of lists. Used to compute average
          studentList GZA = new studentList(64000, "COMP250 - Introduction to Computer Science");
          studentList RZA = new studentList(32000, "MATH240 - Discrete Mathematics");
          int intersection = studentList.intersectionSizeNestedLoops(RZA,GZA);// change method name here to test different algorithms
        }
        
        long wuTangSwordStyle = System.currentTimeMillis();
        long shogunAssassin = wuTangSwordStyle - shaolinShadowBoxing;
        System.out.println("Total run time: "+(shogunAssassin)+" milliseconds");
        System.out.println("Average run time (32000 vs 64000) "+ (((shogunAssassin/50.0) - tigerStyle)+" milliseconds"));
    
        shaolinShadowBoxing = System.currentTimeMillis();
        
        for(int rep = 1; rep <= 50; rep++){ //Iterator for number of lists. Used to compute average
          studentList ghostFace = new studentList(64000, "COMP250 - Introduction to Computer Science");
          studentList inspectahDeck = new studentList(32000, "MATH240 - Discrete Mathematics");
          int intersection = studentList.intersectionSizeNestedLoops(ghostFace,inspectahDeck);// change method name here to test different algorithms
        }
        
        wuTangSwordStyle = System.currentTimeMillis();
        shogunAssassin = wuTangSwordStyle - shaolinShadowBoxing;
        System.out.println("Total run time: "+(shogunAssassin)+" milliseconds");
        System.out.println("Average run time (64000 vs 32000) "+ (((shogunAssassin/50.0) - tigerStyle)+" milliseconds"));
    
  }  
}