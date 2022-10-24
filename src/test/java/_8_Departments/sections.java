package _8_Departments;

import javax.xml.soap.SAAJResult;

public class sections {
    private String name;
    private String shortName;
    private boolean active;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "sections{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", active=" + active +
                ", index=" + index +
                '}';
    }
}
