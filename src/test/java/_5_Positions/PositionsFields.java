package _5_Positions;

public class PositionsFields {
    private String id;
    private String name;
    private String shortName;
    private boolean active;
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    @Override
    public String toString() {
        return "PositionsFields{" +
                " id ='" + id + '\'' +
                ", name ='" + name + '\'' +
                ", shortName ='" + shortName + '\'' +
                ", active =" + active +
                ", tenantId ="+ tenantId;
    }
}
