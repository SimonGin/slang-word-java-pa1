package slang;

import java.util.HashSet;
import java.util.Set;

public class SlangDictionary {
    private Set<SlangWord> dictionary;

    public SlangDictionary() {
        this.dictionary = new HashSet<>();
    }

    public boolean addSlang(SlangWord word) {
        return dictionary.add(word);
    }
}
