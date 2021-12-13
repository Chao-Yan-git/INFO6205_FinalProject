package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.pinyinsort.*;
import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.simple.QuickSort;

import java.util.Arrays;

public class Benchmark_Timer {

    public static void main(String[] args) {
        String[] pinyin;
        String[] a = ChangeToEn.getWords("/shuffledChinese.txt", ChangeToEn::lineAsList, Benchmark_Timer.class);
        String[] b = ChangeToEn.getWords("/shuffledChinese2M.txt", ChangeToEn::lineAsList, Benchmark_Timer.class);
        String[] c = ChangeToEn.getWords("/shuffledChinese4M.txt", ChangeToEn::lineAsList, Benchmark_Timer.class);

        for (int i = 250000; i < 5000000; i *= 2) {
            if (i == 2000000) pinyin = b;
            else if (i == 4000000) pinyin = c;
            else pinyin = Arrays.copyOf(a, i);

            // MSDRadixSort
            Benchmark<String[]> benchmark1 = new Benchmark<>("MSDRadixSort", MSDRadixSort::preProcess, MSDRadixSort::sort);
            double run1 = benchmark1.run(pinyin, 2);
            System.out.println("MSDRadixSort: names = " + i + ", runtime = " + run1);

            // LSDRadixSort
            LSDRadixSort lsd = new LSDRadixSort();
            Benchmark<String[]> benchmark2 = new Benchmark<>("LSDRadixSort", lsd::preProcess, lsd::sort);
            double run2 = benchmark2.run(pinyin, 2);
            System.out.println("LSDRadixSort: names = " + i + ", runtime = " + run2);

            // DualPivot_QuickSort
            Helper<String> helper = new CollatorHelper<>("Helper", i);
            QuickSort<String> sorter = new DualPivotQuickSort<>(helper);
            Benchmark<String[]> benchmark3 = new Benchmark<>("DualPivotQuickSort", sorter::preProcess, sorter::sort);
            double run3 = benchmark3.run(pinyin, 2);
            System.out.println("DualPivotQuickSort: names = " + i + ", runtime = " + run3);

            // HuskySort
            HuskySort<String> huskySort = new HuskySort<>(HuskyCoderFactory.huskySortCoder, false, false);
            Benchmark<String[]> benchmark4 = new Benchmark<>("HuskySort", huskySort::preProcess, huskySort::sort);
            double run4 = benchmark4.run(pinyin, 2);
            System.out.println("HuskySort: names = " + i + ", runtime = " + run4);

            // Timsort
            Benchmark<String[]> benchmark5 = new Benchmark<>("TimSort", TimSort::preProcess, TimSort::sort);
            double run5 = benchmark5.run(pinyin, 2);
            System.out.println("TimSort: names = " + i + ", runtime = " + run5);
        }
    }

}