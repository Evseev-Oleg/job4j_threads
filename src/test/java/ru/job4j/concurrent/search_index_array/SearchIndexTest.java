package ru.job4j.concurrent.search_index_array;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchIndexTest {
    private final Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};


    @Test
    public void whenSearch5Index() {
        int found = 6;
        int start = 0;
        int finish = arr.length - 1;
        SearchIndex<Integer> task = new SearchIndex<>(arr, found, start, finish);
        int res = task.search(task);
        assertEquals(5, res);
    }

    @Test
    public void whenSearchNoElement() {
        int found = 1;
        int start = 10;
        int finish = arr.length - 1;
        SearchIndex<Integer> task = new SearchIndex<>(arr, found, start, finish);
        int res = task.search(task);
        assertEquals(-1, res);
    }
}