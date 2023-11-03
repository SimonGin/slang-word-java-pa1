package slang;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
//        SlangWindow window = new SlangWindow();
//        BufferedReader buffReader = new BufferedReader(new FileReader("slang.txt"));
//        String line = buffReader.readLine();
//        System.out.println(line);
        SlangDictionary dictionary = new SlangDictionary();
        SlangWord word = new SlangWord("&","And");
        SlangWord word2 = new SlangWord("&","An");
        System.out.println(dictionary.addSlang(word));
        System.out.println(dictionary.addSlang(word2));



    }
}