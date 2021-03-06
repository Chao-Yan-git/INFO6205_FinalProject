package edu.neu.coe.info6205.pinyinsort;

import com.ibm.icu.text.Collator;
import edu.neu.coe.info6205.sort.simple.InsertionSortMSD;

import java.util.Arrays;
import java.util.Locale;

/**
 * Class to implement Most significant digit string sort (a radix sort).
 */
public class MSDRadixSort {

    /**
     * Sort an array of Strings using MSDRadixSort.
     *
     * @param a the array to be sorted.
     */
    private static Collator collator = Collator.getInstance(Locale.CHINA);

    public static String[] sort(String[] a) {
        int n = a.length;
        aux = new String[n];
        sort(a, 0, n, 0);
        return a;
    }

    public static String[] preProcess(String[] xs) {
        return Arrays.copyOf(xs, xs.length);
    }

    /**
     * Sort from a[lo] to a[hi] (exclusive), ignoring the first d characters of each String.
     * This method is recursive.
     *
     * @param a  the array to be sorted.
     * @param lo the low index.
     * @param hi the high index (one above the highest actually processed).
     * @param d  the number of characters in each String to be skipped.
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi < lo + cutoff) InsertionSortMSD.sort(a, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.
            for (int i = lo; i < hi; i++) {
                count[sortAt(a[i], d) + 2]++;
            }
            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            for (int i = lo; i < hi; i++)     // Distribute.
                aux[count[sortAt(a[i], d) + 1]++] = a[i];
            // Copy back.
            if (hi - lo >= 0) System.arraycopy(aux, 0, a, lo, hi - lo);
            // Recursively sort for each character value.
            for (int r = 0; r < radix; r++)
                sort(a, lo + count[r], lo + count[r + 1], d + 1);
        }
    }

    private static int sortAt(String s, int d) {
        if (d < s.length()) {
            byte[] bytes = collator.getCollationKey(String.valueOf(s.charAt(d))).toByteArray();
            if (bytes.length < 7) {
                return (bytes[0] & 255) * 255;
            } else
                return (bytes[0] & 255) * 255 + (bytes[1] & 255);
        } else return -1;
    }

    private static final int radix = 65281;
    private static final int cutoff = 15;
    private static String[] aux;      // auxiliary array for distribution

}