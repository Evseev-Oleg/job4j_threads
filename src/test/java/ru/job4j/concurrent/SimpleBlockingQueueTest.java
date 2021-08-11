package ru.job4j.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void queueTest() throws InterruptedException {
        int limit = 10;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(limit);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        try {
                            result.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();

        assertThat(result.get(0), is(0));
        assertThat(result.get(9), is(9));
    }

}