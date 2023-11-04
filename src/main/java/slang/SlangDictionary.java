package slang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    public ArrayList<SlangWord> searchDef(String key_word) {
        ArrayList<SlangWord> foundWords = new ArrayList<>();
        for (SlangWord word : dictionary.values()) {
            String definition = word.getDef();
            if (definition.contains(key_word)) {
                foundWords.add(word);
            }
        }
        return foundWords;
    }

    public void deleteSlang(String key) {
        dictionary.remove(key);
    }
}
