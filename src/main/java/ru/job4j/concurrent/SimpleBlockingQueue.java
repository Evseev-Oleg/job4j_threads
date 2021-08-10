package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        if (queue.size() == limit) {
            notifyAll();
        }

        return queue.remove();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue =
                new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {

                    synchronized (simpleBlockingQueue) {
                        while (true) {
                            try {
                                simpleBlockingQueue.offer(new Random().nextInt(100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        Thread consumer = new Thread(
                () -> {
                    synchronized (simpleBlockingQueue) {
                        while (true) {
                            try {
                                System.out.println(simpleBlockingQueue.poll());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

}
