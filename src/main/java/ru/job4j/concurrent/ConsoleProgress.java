package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = {"\\", "|", "/", "--"};
        int i = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[i]);
                Thread.sleep(500);
                i++;
                if (i == 4) {
                    i = 0;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(4000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
