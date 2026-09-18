package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        runBenchmark();
    }

    public static void runBenchmark() {
        // Требуемые размеры массивов и типы входных данных[cite: 1]
        int[] sizes = {1000, 10000, 100000, 1000000};
        String[] inputs = {"random", "sorted", "duplicates"};
        String[] algorithms = {"MergeSort", "QuickSort", "QuickSelect"};

        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (String algo : algorithms) {
                for (String input : inputs) {
                    for (int n : sizes) {
                        System.out.println("Running " + algo + " | " + input + " | " + n);

                        Metrics medianMetrics = run5TimesAndGetMedian(algo, input, n);

                        writer.write(String.format("%s,%s,%d,%.4f,%d,%d\n",
                                algo, input, n, medianMetrics.getTimeMs(),
                                medianMetrics.getComparisons(), medianMetrics.getMaxDepth()));
                    }
                }
            }
            System.out.println("Benchmark finished! Results saved to results.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private static Metrics run5TimesAndGetMedian(String algo, String inputType, int n) {
        Metrics[] runs = new Metrics[5];
        for (int i = 0; i < 5; i++) {
            int[] arr = generateArray(inputType, n);
            runs[i] = new Metrics();

            if (algo.equals("MergeSort")) {
                MergeSort.sort(arr, runs[i]);
            } else if (algo.equals("QuickSort")) {
                QuickSort.sort(arr, runs[i]);
            } else if (algo.equals("QuickSelect")) {
                QuickSelect.select(arr, n / 2, runs[i]);
            }
        }

        Arrays.sort(runs, (m1, m2) -> Double.compare(m1.getTimeMs(), m2.getTimeMs()));
        return runs[2];
    }

    private static int[] generateArray(String type, int n) {
        int[] arr = new int[n];
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt();
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) arr[i] = i;
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(10);
        }
        return arr;
    }
}