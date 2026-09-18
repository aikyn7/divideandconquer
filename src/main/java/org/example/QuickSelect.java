package org.example;

import java.util.Random;

public class QuickSelect {
    private static final Random RANDOM = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        // 1. Проверка на невалидные данные с выбросом исключения
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Array is empty or k is out of bounds");
        }

        long startTime = System.nanoTime();

        int result = selectRecursive(a, k, 0, a.length - 1, 1, metrics);

        metrics.setTimeNano(System.nanoTime() - startTime);
        return result;
    }

    private static int selectRecursive(int[] a, int k, int left, int right, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        // Базовый случай: если остался один элемент, это и есть ответ
        if (left == right) {
            return a[left];
        }

        // Выбираем случайный опорный элемент
        int pivotIndex = left + RANDOM.nextInt(right - left + 1);
        int pivot = a[pivotIndex];

        // 2. Используем то же самое трехстороннее разбиение, что и в QuickSort
        int lt = left;
        int gt = right;
        int i = left;

        while (i <= gt) {
            metrics.addComparison();
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else {
                metrics.addComparison();
                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }
        }

        // 3. Продолжаем поиск только в одной части массива[cite: 1]
        if (k >= lt && k <= gt) {
            // Элемент k находится в зоне элементов, равных pivot
            return pivot;
        } else if (k < lt) {
            // Искомый элемент в левой части (меньше pivot)
            return selectRecursive(a, k, left, lt - 1, depth + 1, metrics);
        } else {
            // Искомый элемент в правой части (больше pivot)
            return selectRecursive(a, k, gt + 1, right, depth + 1, metrics);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}