import java.io.*;
import java.util.Random;
 
class GeneratingRandomArrays {
    static int arrayTotal = 5;
    static int lowerBound = 0;
    static int upperBound = 1000;
    static int minSize = 100;
    static int maxSize = 200;
    
    public static void main (String[] args) throws IOException {
        Random random = new Random();
        PrintWriter writer = new PrintWriter("random-array.txt", "UTF-8");

        writer.println(arrayTotal);
        for(int i = 0; i < arrayTotal; i++) {
            int size = random.nextInt(maxSize - minSize) + minSize;
            writer.println(size);
 
            for(int j = 0; j < size; j++) {
                int a = random.nextInt(upperBound - lowerBound) + lowerBound;
                writer.print(a + " ");
            }
            writer.println();
        }
        writer.close();

        writer = new PrintWriter("sorted-array.txt", "UTF-8");
        writer.println(arrayTotal);
        for(int i = 0; i < arrayTotal; i++) {
            int size = random.nextInt(maxSize - minSize) + minSize;
            writer.println(size);
 
            for(int j = 0; j < size; j++) {
                writer.print(j + " ");
            }
            writer.println();
        }
        writer.close();

        writer = new PrintWriter("reverse-sorted-array.txt", "UTF-8");
        writer.println(arrayTotal);
        for(int i = 0; i < arrayTotal; i++) {
            int size = random.nextInt(maxSize - minSize) + minSize;
            writer.println(size);
 
            for(int j = size-1; j >= 0; j--) {
                writer.print(j + " ");
            }
            writer.println();
        }
        writer.close();
    }
}