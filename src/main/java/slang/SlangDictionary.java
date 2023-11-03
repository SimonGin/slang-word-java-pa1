package slang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SlangDictionary {
    private HashMap<String,SlangWord> dictionary;

    public HashMap<String, SlangWord> getDictionary() {
        return dictionary;
    }

    public SlangDictionary() {
        this.dictionary = new HashMap<>();
    }

    public boolean addSlang(SlangWord word) {
        if (dictionary.containsKey(word.getKey())) {
            return false;
        }
        dictionary.put(word.getKey(), word);
        return true;
    }

    public void importFile() throws IOException {
        BufferedReader buffReader = new BufferedReader(new FileReader("slang.txt"));
        String line;
        while ((line = buffReader.readLine()) != null) {
            String[] parts = line.split("`");
            SlangWord newWord = new SlangWord(parts[0],parts[1]);
            addSlang(newWord);
        }
        buffReader.close();
    }

    public SlangWord searchKey(String key) {
        if (!dictionary.containsKey(key)) {
            return null;
        }
        return dictionary.get(key);
    }

    public SlangWord searchDef(String key_word) {
        return null;
    }
}
