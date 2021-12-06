import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static java.lang.Math.sqrt;
import java.io.*;
import java.util.Scanner;

public class BlockSort {
    public static int[] array;
    public static int[] mergedArray;
    public static int[] sortedArray;
    public static int[] finalArray;
    public static int bufferController = 0;
    public static int arrSize;
    public static double buffer;
    public static double numBlocks;

    public static void main(String[] args) {
        try {
            Scanner randomArray = new Scanner(new File("random-array.txt"));
            Scanner sortedArray = new Scanner(new File("sorted-array.txt"));
            Scanner reverseSortedArray = new Scanner(new File("reverse-sorted-array.txt"));
            Scanner almostSortedArray = new Scanner(new File("almost-sorted-array.txt"));
            Scanner reverseAlmostSortedArray = new Scanner(new File("reverse-almost-sorted-array.txt"));
            runBlockSort(randomArray, "Random Array");
            runBlockSort(sortedArray, "Sorted Array");
            runBlockSort(reverseSortedArray, "Reverse Sorted Array");
            runBlockSort(almostSortedArray, "Almost Sorted Array");
            runBlockSort(reverseAlmostSortedArray, "Reverse Almost Sorted Array");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void runBlockSort(Scanner s, String name) {
        int total = s.nextInt();
        for (int i = 0; i < total; i++){
            int size = s.nextInt();
            int[] newArray = new int[size];
            for (int j = 0; j < size; j++){
                newArray[j] = s.nextInt();
            }
            long start = System.currentTimeMillis();
            arrSize = newArray.length;
            array = (int[])newArray.clone();
            arrBuffer();
            long elapsedTimeMillis = System.currentTimeMillis()-start;
            System.out.println(name + " size " + size + " time " + elapsedTimeMillis);
        }
    }
    
    public static void initializeArray() {
        Random rn = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rn.nextInt(40);
        }
    }

    public static void arrBuffer() {
        buffer = Math.sqrt(arrSize);
        buffer = (int)buffer;
        numBlocks = arrSize / buffer;

        int[] temp = new int[(int)buffer];
        bufferController = 0;
        mergedArray = new int[(int)buffer];

        if ((arrSize % buffer) == 0) {
            for (int i = 0; i < arrSize; i = i + (int)buffer) {
                for (int ii = bufferController; ii < bufferController + (int)buffer && ii < array.length; ii++) {
                    temp[ii % (int)buffer] = array[ii];
                }

                bufferController = bufferController + (int)buffer;
                mergeExtract(temp, (int)numBlocks, (int)buffer, 0);
            }
            numBlocks = (int)numBlocks;
        } else {
            double remainingPart = arrSize % buffer;
            for (int i = 0; i < arrSize; i = i + (int)buffer) {
                for (int ii = bufferController; ii < bufferController + (int)buffer && ii < array.length; ii++) {
                    temp[ii % (int)buffer] = array[ii];
                }
                bufferController = bufferController + (int)buffer;

                if (i == numBlocks * buffer - remainingPart) {
                    mergeExtract(temp, (int)numBlocks, (int)remainingPart, (int)remainingPart);
                } else {
                    mergeExtract(temp, (int)numBlocks, (int)buffer, (int)remainingPart);
                }
            }
            numBlocks = (int)numBlocks;
        }
    }
    
    private static void mergeExtract(int[] arr, int numBlocks, int buffer, int remainingPart) {
        List<Integer> unsorted = new ArrayList<Integer>();
        List<Integer> sorted;
        if (remainingPart != 0) {
            for (int x = 0; x < buffer; x++) {
                unsorted.add(arr[x]);
            }
        } else {
            for (int x = 0; x < buffer; x++) {
                unsorted.add(arr[x]);
            }
        }

        sorted = MergeSort(unsorted);
        int[] sortedArray = sorted.stream().mapToInt(i -> i).toArray();
        mergeBlocks(sortedArray, bufferController);
    }

    private static List<Integer> MergeSort(List<Integer> unsorted) {
        if (unsorted.size() <= 1) {
            return unsorted;
        }

        List<Integer> Left = new ArrayList<Integer>();
        List<Integer> Right = new ArrayList<Integer>();
        int middle = unsorted.size() / 2;
        for (int i = 0; i < middle; i++) {
            Left.add(unsorted.get(i));
        }

        for (int i = middle; i < unsorted.size(); i++) {
            Right.add(unsorted.get(i));
        }

        Left = MergeSort(Left);
        Right = MergeSort(Right);

        return Merge(Left, Right);
    }

    private static List<Integer> Merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<Integer>();
        while (left.size() > 0 || right.size() > 0) {
            if (left.size() > 0 && right.size() > 0) {
                if (left.get(0) <= right.get(0)) {
                    result.add(left.get(0));
                    left.remove(left.get(0));
                }
                else {
                    result.add(right.get(0));
                    right.remove(right.get(0));
                }
            }
            else if (left.size() > 0) {
                result.add(left.get(0));
                left.remove(left.get(0));
            }
            else if (right.size() > 0) {
                result.add(right.get(0));
                right.remove(right.get(0));
            }
        }
        return result;
    }

    private static void mergeBlocks(int[] arr, int increaseBy) {
        int remainingPart = arr.length % (int)buffer;
        int test = increaseBy - (int)buffer;
        if (arr.length % (int)buffer == 0) {
            for (int x = 0; x < arr.length; x++) {                
                mergedArray[test] = arr[x % mergedArray.length];
                test++;
            }

            finalArray = mergedArray;

        } else {
            int[] array = new int[mergedArray.length + remainingPart - (int)buffer];
            
            if (array.length > mergedArray.length) {
                for (int i=0 ; i < mergedArray.length; i++){
                    array[i] = mergedArray[i];
                }
            } else {
                for (int i=0 ; i < array.length; i++){
                    array[i] = mergedArray[i];
                }
            }

            mergedArray = (int[])array.clone();
            
            for (int x = 0; x < remainingPart; x++){
                mergedArray[test] = arr[x % mergedArray.length];
                test++;
            }

            finalArray = (int[])mergedArray.clone();
        }
        
        insertionSort(mergedArray);

        if (mergedArray.length <= array.length) {
            int[] array = new int[arr.length + increaseBy - remainingPart];

            finalArray = (int[])mergedArray.clone();

            if(array.length > mergedArray.length) {
                for (int i=0 ; i < mergedArray.length; i++){
                    array[i] = mergedArray[i];
                }
            } else {
                for (int i=0 ; i < array.length; i++){
                    array[i] = mergedArray[i];
                }
            }

            mergedArray = (int[])array.clone();
        }
    }

    private static void insertionSort(int[] arr) {
        int i, key, j;
        for (i = 1; i < arr.length; i++) {
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void printfinal() {
        System.out.println("Printing The Sorted Array");
        for (int item : finalArray)
        {
            System.out.print(item + " ");
        }
    }
}