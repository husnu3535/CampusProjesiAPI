package _8_Departments;

public class school {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "school{" +
                "id='" + id + '\'' +
                '}';
    }
}
