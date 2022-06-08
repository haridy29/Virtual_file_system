package main.util;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public static void write(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
