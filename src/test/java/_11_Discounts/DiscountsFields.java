package _11_Discounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscountsFields {
    private String id;
    private String code;
    private String description;
    private String priority;
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }
    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DiscountsFields{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", active=" + active +
                '}';
    }
}
