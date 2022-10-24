package _4_Fields;

import javax.xml.soap.SAAJResult;

public class FieldsAndFields {
    private String id;
    private String name;
    private String code;
    private String type;
    private String schoolId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "FieldsAndFields{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
