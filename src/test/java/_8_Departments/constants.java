package _8_Departments;

public class constants {
    private String key;
    private String value;
    private int index;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "constants{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", index=" + index +
                '}';
    }
}

