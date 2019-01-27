package edu.neu.coe.info6205.sort.simple;

import static edu.neu.coe.info6205.sort.simple.Helper.less;
import static edu.neu.coe.info6205.sort.simple.Helper.swap;

public class InsertionSort<X extends Comparable<X>> implements Sort<X> {
    @Override
    public void sort(X[] xs, int from, int to) {
        // TODO implement selection sort


        int N = xs.length;

        for (int i = 1; i < N; i++)

        { // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..

            for (int j = i; j > 0 && less(xs[j], xs[j-1]); j--)
                exch(xs, j, j-1);

        }


    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b)<0; //maybe false?
  }
}
