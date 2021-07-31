package ru.job4j.concurrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ParseFileGet {
    private final File file;

    public ParseFileGet(final File file) {
        this.file = file;
    }

    public String getContentWithoutUnicode(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)){
            int data;
            while ((data = i.read()) > 0) {
                    if (filter.test((char)data)) {
                        output.append((char) data);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
