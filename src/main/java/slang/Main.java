package slang;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SlangDictionary dictionary = new SlangDictionary();
        dictionary.importFile();

        SlangWindow window = new SlangWindow(dictionary);

    }
}