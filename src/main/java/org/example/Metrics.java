package org.example;
public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;
    private long timeNano = 0;

    public void addComparison() {
        comparisons++;
    }

    public void addComparisons(int count) {
        comparisons += count;
    }

    public void updateDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void setTimeNano(long timeNano) {
        this.timeNano = timeNano;
    }

    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }

    public double getTimeMs() {
        return timeNano / 1_000_000.0;
    }
}