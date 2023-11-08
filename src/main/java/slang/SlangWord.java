package slang;

public class SlangWord {
    private final String key;
    private final String def;

    public SlangWord(String key, String def) {
        this.key = key;
        this.def = def;
    }

    public String getKey() {
        return key;
    }

    public String getDef() {
        return def;
    }
}
