package ru.job4j.concurrent;

import java.io.*;

public class ParseFileSave {
    private final File file;

    public ParseFileSave(final File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)){
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
