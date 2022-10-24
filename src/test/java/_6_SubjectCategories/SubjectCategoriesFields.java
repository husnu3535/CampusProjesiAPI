package _6_SubjectCategories;

public class SubjectCategoriesFields {
    private String id;
    private String name;
    private String code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public String toString() {
        return "SubjectCategoriesFields{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                '}';
    }
}
