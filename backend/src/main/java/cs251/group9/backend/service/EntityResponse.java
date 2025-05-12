package cs251.group9.backend.service;

public class EntityResponse {

    private String entityName;
    private String entityType;
    private String entityId;
    private String entityDescription;

    public EntityResponse(String entityName, String entityType, String entityId, String entityDescription) {
        this.entityName = entityName;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityDescription = entityDescription;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

}
