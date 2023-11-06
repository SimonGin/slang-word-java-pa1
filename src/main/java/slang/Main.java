package slang;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SlangDictionary dictionary = new SlangDictionary();
        dictionary.loadFile("slang-new.txt");

        SlangWindow window = new SlangWindow(dictionary);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                dictionary.exportNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
//        new SlangGameFrame();
    }
}