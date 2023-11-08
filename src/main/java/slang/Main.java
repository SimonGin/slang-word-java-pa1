package slang;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SlangDictionary dictionary = new SlangDictionary("slang-new.txt");

        new SlangWindow(dictionary);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                dictionary.exportNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}