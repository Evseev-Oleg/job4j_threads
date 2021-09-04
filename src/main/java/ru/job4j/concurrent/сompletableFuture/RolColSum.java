package ru.job4j.concurrent.сompletableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum
                    + ", colSum=" + colSum + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = searchSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            futures.put(i, CompletableFuture.supplyAsync(() -> searchSums(matrix, finalI)));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static Sums searchSums(int[][] matrix, int index) {
        int sumRow = 0;
        int sumCol = 0;
        for (int i = 0; i < matrix.length; i++) {
            sumRow += matrix[index][i];
            sumCol += matrix[i][index];
        }
        Sums sums = new Sums();
        sums.setColSum(sumCol);
        sums.setRowSum(sumRow);
        return sums;
    }
}
