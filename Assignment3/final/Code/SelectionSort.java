package edu.neu.coe.info6205.sort.simple;

import static edu.neu.coe.info6205.sort.simple.Helper.less;
import static edu.neu.coe.info6205.sort.simple.Helper.swap;

public class SelectionSort<X extends Comparable<X>> implements Sort<X> {

    @Override
    public void sort(X[] xs, int from, int to) {
        // TODO implement selection sort


        int N = xs.length;
        for(int i=0; i< N; i++){
            int min = i;
            for(int j = i+1; j<N; j++)
                if(less(xs[j],xs[min])) min=j;
            exch(xs,i,min);
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
