package _8_Departments;

import java.util.ArrayList;

public class DepartmentsFields {
    private String id;
    private String name;
    private String code;
    private boolean active;
    private ArrayList <constants> constants;
    private ArrayList <sections> sections;
    private school school;

    public _8_Departments.school getSchool() {
        return school;
    }

    public void setSchool(_8_Departments.school school) {
        this.school = school;
    }

    public ArrayList<_8_Departments.constants> getConstants() {
        return constants;
    }

    public void setConstants(ArrayList<_8_Departments.constants> constants) {
        this.constants = constants;
    }

    public ArrayList<_8_Departments.sections> getSections() {
        return sections;
    }

    public void setSections(ArrayList<_8_Departments.sections> sections) {
        this.sections = sections;
    }

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
        return "DepartmentsFields{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                '}';
    }
}
