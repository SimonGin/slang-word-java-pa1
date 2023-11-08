package slang;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlangDictionary {
    private HashMap<String,SlangWord> dictionary;

    public HashMap<String, SlangWord> getDictionary() {
        return dictionary;
    }

    public SlangDictionary(String file) throws IOException {
        this.dictionary = new HashMap<>();
        this.loadFile(file);
    }

    public boolean addSlang(SlangWord word) {
        if (dictionary.containsKey(word.getKey())) {
            return false;
        }
        dictionary.put(word.getKey(), word);
        return true;
    }

    public boolean updateSlang(SlangWord word) {
        if (word.getDef().isBlank()) {
            return false;
        }
        dictionary.put(word.getKey(),word);
        return true;
    }

    public void loadFile(String fileName) throws IOException {
        File newFile = new File(fileName);
        if (newFile.exists() && newFile.isFile()) {
            BufferedReader newBuffReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = newBuffReader.readLine()) != null) {
                String[] parts = line.split("`");
                SlangWord newWord = new SlangWord(parts[0],parts[1]);
                updateSlang(newWord);
            }
            newBuffReader.close();
            return;
        }
        BufferedReader orgBuffReader = new BufferedReader(new FileReader("slang-org.txt"));
        String line;
        while ((line = orgBuffReader.readLine()) != null) {
            String[] parts = line.split("`");
            SlangWord newWord = new SlangWord(parts[0],parts[1]);
            updateSlang(newWord);
        }
        orgBuffReader.close();
    }

     public void exportNewFile() throws IOException {
        BufferedWriter buffWriter = new BufferedWriter(new FileWriter("slang-new.txt"));
        for (Map.Entry<String,SlangWord> entry : dictionary.entrySet()) {
            String key = entry.getKey();
            String def = entry.getValue().getDef();
            buffWriter.write(key + '`' + def + '\n');
        }
        buffWriter.close();
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

    public SlangWord randomizeSlang() {
        String[] keyArr = dictionary.keySet().toArray(new String[0]);
        Random random = new Random();
        int randomIdx = random.nextInt(keyArr.length);
        String randomKey = keyArr[randomIdx];
        return dictionary.get(randomKey);
    }

    public void deleteSlang(String key) {
        dictionary.remove(key);
    }
}
