package com.dsep.util;
public class CountInvertedSeqUtil {   
    static int count=0;
    
   public static int getInvertedSeqNum(int[] arr){
	   int result = sort(arr);
	   count = 0;
	   return result;
   }
          
   public static int sort(int arr[]) {
      int temp[] = new int[arr.length];   
      return mergeSort(arr,temp,0,arr.length-1);   
   }   
      
   private static int mergeSort(int arr[],int temp[],int m,int n) { 
       if (m == n) return count;   
       int mid = (m+n)/2;   
       mergeSort(arr, temp, m, mid);   
       mergeSort(arr, temp, mid+1, n);   
       for (int i = m; i <= n; i++) {   
           temp[i] = arr[i];   
       }   
       int index1 = m;   
       int index2 = mid + 1;   
       int index = m;   
       while (index1 <= mid && index2 <= n) {   
           if (temp[index1] <= temp[index2]) {   
               arr[index++] = temp[index1++];   
           } else {   
               arr[index++] = temp[index2++];   
               count=count+mid-index1+1;
           }   
       }   
       while(index1 <= mid) {   
           arr[index++] = temp[index1++];   
       }   
       while(index2 <= n) {   
           arr[index++] = temp[index2++];   
       }   
      return count;    
   }   
 
}  