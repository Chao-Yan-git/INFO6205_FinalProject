package edu.neu.coe.info6205.pinyinsort;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DualPivotQuickSortTest {

    String[] input = new String[]{"王诗卉", "高伟凯", "蒋开文", "党权", "杨心", "王琴波", "孟会", "武彬", "王卫光", "黄斯琪"};
    String[] expected = new String[]{"党权", "高伟凯", "黄斯琪", "蒋开文", "孟会", "王琴波", "王诗卉", "王卫光", "武彬", "杨心"};

    @Test
    public void sort1() {
        String[] res = DualPivotQuickSort.sort(input);
        assertArrayEquals(expected, res);
    }

    @Test
    public void sort2() {
        String[] words = ChangeToEn.getWords("/shuffledChinese.txt", ChangeToEn::lineAsList, DualPivotQuickSortTest.class);
        String[] s = DualPivotQuickSort.sort(words);
        String[] sorted = Arrays.copyOfRange(s, 0, 1000000);
        ChangeToEn.writeWords(sorted, String.join(File.separator, "src", "main", "resources", "DualpivotQuicksort_Result.txt"));
        assertEquals("阿安", s[0]);
    }

}
