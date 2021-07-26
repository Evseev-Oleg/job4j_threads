package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget2 implements Runnable {
    private final String file;
    private final String url;
    private final int speed;

    public Wget2(String file, String url, int speed) {
        this.file = file;
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            long finishTime = System.currentTimeMillis() - startTime;
            if (finishTime < speed) {
                Thread.sleep(speed - finishTime);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget2(file, url, speed));
        wget.start();
        wget.join();
    }
}
