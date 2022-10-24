package _7_SchoolLocations;

public class SchoolLocationsFields {
    private String id;
    private String name;
    private String shortName;
    private boolean active;
    private String capacity;
    private String type;
    private school school;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public _7_SchoolLocations.school getSchool() {
        return school;
    }

    public void setSchool(_7_SchoolLocations.school school) {
        this.school = school;
    }
}