// package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    // private static int arraySize = 10000;
    public static void main(String[] args) {
        int n = 0;
        try{
            n = Integer.parseInt(args[0]); // n is the total sites
            if(n<0&&n>10000){
                System.out.println("Please type a value between 0~10000,because the arraySize is set to 10000");
                return;
            }
        }catch(NumberFormatException e){
            System.out.println("wrong input, number only");
            return;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("There is no input");
            return;
        }

        if (n>0)
            ParSort.cutoff = Integer.parseInt(args[0]);
        Random random = new Random();
        int[] array = new int[10000];
            for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000);
            ParSort.sort(array, 0, array.length);







//         System.out.println(n);
//         processArgs(args);
//         System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
//         Random random = new Random();
        // int[] array = new int[arraySize];
        ArrayList<Long> timeList = new ArrayList<>();
//         System.out.println(n);


//         //warm up
//         // System.currentTimeMillis();
//         // ParSort.cutoff = 100;
//         // System.out.println("warm up start");
//         // for(int t=0; t<10; t++){
//         //     for(int i =0; i<array.length; i++) array[i] = random.nextInt(100);
//         //     ParSort.sort(array,0,array.length);
//         // }
//         // System.out.println("warm up finished");
//         // System.currentTimeMillis();



        // for (double j = 100; j >= 5; j /= 2) {
            // ParSort.cutoff = (int)(n);
//            ParSort.cutoff = 10000 * (j + 1);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);


            System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

//         // }
//         try {
//             FileOutputStream fis = new FileOutputStream("result.csv");
//             OutputStreamWriter isr = new OutputStreamWriter(fis);
//             BufferedWriter bw = new BufferedWriter(isr);
//             int j = 100;
//             bw.write("Cutoff(%),Time(ms)\n");
//             bw.flush();
//             for (long i : timeList) {
// //                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
//                 String content = (double) arraySize/100 * j /arraySize + "," +(double) i/10 + "\n";
// //                j++;
//                 j /= 2;
//                 bw.write(content);
//                 bw.flush();
//             }
//             bw.close();

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
