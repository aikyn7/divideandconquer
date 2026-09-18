package org.example;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        long startTime = System.nanoTime();

        sortRecursive(arr, 0, arr.length - 1, 1, metrics);

        metrics.setTimeNano(System.nanoTime() - startTime);
    }

    private static void sortRecursive(int[] arr, int left, int right, int depth, Metrics metrics) {
        while (left < right) {
            metrics.updateDepth(depth);

            int pivotIndex = left + RANDOM.nextInt(right - left + 1);
            int pivot = arr[pivotIndex];

            int lt = left;
            int gt = right;
            int i = left;

            while (i <= gt) {
                metrics.addComparison();
                if (arr[i] < pivot) {
                    swap(arr, lt, i);
                    lt++;
                    i++;
                } else {
                    metrics.addComparison();
                    if (arr[i] > pivot) {
                        swap(arr, i, gt);
                        gt--;
                    } else {
                        i++;
                    }
                }
            }

            int leftSize = lt - 1 - left;
            int rightSize = right - (gt + 1);

            if (leftSize < rightSize) {
                sortRecursive(arr, left, lt - 1, depth + 1, metrics);
                left = gt + 1;
            } else {
                sortRecursive(arr, gt + 1, right, depth + 1, metrics);
                right = lt - 1;
            }
            depth++;
        }
        metrics.updateDepth(depth);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}