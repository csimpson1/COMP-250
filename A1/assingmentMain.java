 /* The main method */
    /* Write code here to test your methods, and evaluate the running time of each. 2013 */
    /* This method will not be marked */
    public static void main(String args[]) throws Exception {
 
 studentList firstList;
 studentList secondList;
 
 // This is how to read lists from files. Useful for debugging.
 
 // firstList=new studentList("COMP250.txt", "COMP250 - Introduction to Computer Science");
 // secondList=new studentList("MATH240.txt", "MATH240 - Discrete Mathematics");
 
 // get the time before starting the intersections
 long startTime = System.currentTimeMillis();
 
 // repeat the process a certain number of times, to make more accurate average measurements.
 for (int rep=0;rep<100;rep++) {
     
     // This is how to generate lists of random IDs. 
     // For firstList, we generate 16000 IDs
     // For secondList, we generate 16000 IDs
     
     firstList=new studentList(16000 , "COMP250 - Introduction to Computer Science"); 
     secondList=new studentList(16000 , "MATH240 - Discrete Mathematics"); 
     
     // run the intersection method
     int intersection=studentList.intersectionSizeBinarySearch(firstList,secondList);
     System.out.println("The intersection size is: "+intersection);
 }
 
 // get the time after the intersection
 long endTime = System.currentTimeMillis();
 
 
 System.out.println("Running time: "+ (endTime-startTime) + " milliseconds");
 }