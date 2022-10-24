package _3_DocumentTypes;

import java.util.Arrays;

public class DocumentTypesFields {
    private String id;
    private String name;
    private String description;
    private int [] attachmentStages;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[]  getAttachmentStages() {
        return attachmentStages;
    }

    public void setAttachmentStages(int[]  attachmentStages) {
        this.attachmentStages = attachmentStages;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "DocumentTypesFields{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attachmentStages=" + (attachmentStages) +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
