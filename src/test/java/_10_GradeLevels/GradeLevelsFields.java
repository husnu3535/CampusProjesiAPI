package _10_GradeLevels;

public class GradeLevelsFields {
    private String id;
    private String name;
    private String  shortName;
    private nextGradeLevel nextGradeLevel;
    private String order;
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public _10_GradeLevels.nextGradeLevel getNextGradeLevel() {
        return nextGradeLevel;
    }

    public void setNextGradeLevel(_10_GradeLevels.nextGradeLevel nextGradeLevel) {
        this.nextGradeLevel = nextGradeLevel;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "GradeLevelsFields{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", nextGradeLevel=" + nextGradeLevel +
                ", order='" + order + '\'' +
                ", active=" + active +
                '}';
    }
}
