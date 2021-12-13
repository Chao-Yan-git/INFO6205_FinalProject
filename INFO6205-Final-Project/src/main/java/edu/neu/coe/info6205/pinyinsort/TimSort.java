package edu.neu.coe.info6205.pinyinsort;

import com.ibm.icu.text.Collator;
import java.util.Arrays;
import java.util.Locale;

/**
 * Sorter which delegates to Timsort via Arrays.sort.
 */
public class TimSort {
    static Collator collator = Collator.getInstance(Locale.CHINA);

    public static final String DESCRIPTION = "Timsort";

    public static String[] preProcess(String[] a) {
        return Arrays.copyOf(a, a.length);
    }

    /**
     * @param xs   sort the array xs from "from" until "to" (exclusive of to).
     */
    public static String[] sort(String[] xs) {
        String[] s = preProcess(xs);
        Arrays.sort(s, collator::compare);
        return s;
    }

    /**
     * Constructor for TimSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */

}
