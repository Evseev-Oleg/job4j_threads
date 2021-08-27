package ru.job4j.concurrent.search_index_array;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V v;
    private final Integer start;
    private final Integer finish;

    public SearchIndex(V[] array, V v, Integer start, Integer finish) {
        this.array = array;
        this.v = v;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread());
        int size = finish - start;
        if (size > 10) {
            int mid = (start + finish) / 2;
            SearchIndex<V> task1 = new SearchIndex<>(array, v, start, mid);
            SearchIndex<V> task2 = new SearchIndex<>(array, v, mid + 1, finish);
            task1.fork();
            task2.fork();
            int res1 = task1.join();
            int res2 = task2.join();
            if (res1 != -1 || res2 != -1) {
                return res1 == -1 ? res2 : res1;
            }
        }
        return linearSort(this.v);
    }

    private int linearSort(V v) {
        for (int i = 0; i < finish; i++) {
            if (array[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int found = 90;
        int start = 0;
        int finish = arr.length;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        SearchIndex<Integer> task = new SearchIndex<>(arr, found, start, finish);
        int res = pool.invoke(task);
        System.out.println(res);
    }
}
