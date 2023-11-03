package slang;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
//        SlangWindow window = new SlangWindow();

        SlangDictionary dictionary = new SlangDictionary();
        dictionary.importFile();



    }
}