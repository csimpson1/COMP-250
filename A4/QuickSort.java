public class QuickSort{
  
  public static void switchIndex(int [] a, int index1, int index2){
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;   
  }
  
  public static int partition(int [] a, int start, int stop){
    int left, right, pivot;
    pivot = a[stop];
    left = start;
    right = stop-1;
    
    while(left <= right){
      while(left <= right && a[left] <= pivot){left ++;}
      while(left <= right && a[right] >= pivot){right--;}
      if(left < right){switchIndex(a,left, right);}
    }
    switchIndex(a,left, stop);  
    return left;  
  }
  
  public static void quickSort(int[] a, int start, int stop){
    if(start<stop){
      int pivot = partition(a, start, stop);
      quickSort(a, start, pivot-1);
      quickSort(a, pivot+1, stop);
    }
  }
  public static void main(String[] args){
    int[] GZA = {4,8,5,2,3,1,7,6};
    int j = partition(GZA, 0, 7);
    
    for(int i = 0; i < GZA.length; i++){
      System.out.print(GZA[i]+" ");
    }
      
  }
    
}