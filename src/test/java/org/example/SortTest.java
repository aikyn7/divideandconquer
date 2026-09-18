package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class SortTest {
    private static final Random RANDOM = new Random();

    @Test
    public void testMergeAndQuickSortCorrectness() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(1000);
            int[] expected = arr.clone();
            int[] mergeArr = arr.clone();
            int[] quickArr = arr.clone();

            Arrays.sort(expected);
            MergeSort.sort(mergeArr, new Metrics());
            QuickSort.sort(quickArr, new Metrics());

            assertArrayEquals(expected, mergeArr, "MergeSort failed");
            assertArrayEquals(expected, quickArr, "QuickSort failed");
        }
    }

    @Test
    public void testQuickSortDepthOnSortedArray() {
        int n = 100000;
        int[] sortedArr = new int[n];
        for (int i = 0; i < n; i++) sortedArr[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(sortedArr, metrics);

        int maxAllowedDepth = (int) (2 * (Math.log(n) / Math.log(2)));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Depth exceeded: " + metrics.getMaxDepth() + " > " + maxAllowedDepth);
    }

    @Test
    public void testQuickSelectCorrectness() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(100);
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = RANDOM.nextInt(arr.length);
            int result = QuickSelect.select(arr, k, new Metrics());

            assertEquals(sorted[k], result, "QuickSelect failed for k=" + k);
        }
    }

    @Test
    public void testEdgeCases() {
        int[] empty = {};
        MergeSort.sort(empty, new Metrics());
        QuickSort.sort(empty, new Metrics());
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, new Metrics()));

        int[] single = {42};
        MergeSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        int[] equal = {7, 7, 7, 7, 7};
        int[] quickEqual = equal.clone();
        QuickSort.sort(quickEqual, new Metrics());
        assertArrayEquals(equal, quickEqual);

        int[] sorted = {1, 2, 3, 4, 5};
        int[] mergeSorted = sorted.clone();
        MergeSort.sort(mergeSorted, new Metrics());
        assertArrayEquals(sorted, mergeSorted);
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(10000);
        }
        return arr;
    }
}