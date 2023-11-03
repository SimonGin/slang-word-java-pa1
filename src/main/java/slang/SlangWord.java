package slang;

import java.util.Objects;

public class SlangWord {
    private String key;
    private String def;

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

    public void setDef(String def) {
        this.def = def;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "{ key='" + key + '\'' +
                ", def='" + def + '\'' +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlangWord slangWord = (SlangWord) o;
        return Objects.equals(key, slangWord.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
