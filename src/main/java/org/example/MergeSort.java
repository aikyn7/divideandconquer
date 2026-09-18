package org.example;

public class MergeSort {

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;

        int[] buffer = new int[arr.length];

        long startTime = System.nanoTime();

        sortRecursive(arr, buffer, 0, arr.length - 1, 1, metrics);

        metrics.setTimeNano(System.nanoTime() - startTime);
    }

    private static void sortRecursive(int[] arr, int[] buffer, int left, int right, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        if (right - left + 1 <= 15) {
            insertionSort(arr, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        sortRecursive(arr, buffer, left, mid, depth + 1, metrics);
        sortRecursive(arr, buffer, mid + 1, right, depth + 1, metrics);

        merge(arr, buffer, left, mid, right, metrics);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.addComparison();
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= left) {
                metrics.addComparison();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }
}