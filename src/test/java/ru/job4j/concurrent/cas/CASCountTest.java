package ru.job4j.concurrent.cas;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {
    @Test
    public void casCountTest() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 50; i++) {
                        count.increment();
                    }
                }
        );

        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 50; i++) {
                        count.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(count.get(), is(100));
    }
}