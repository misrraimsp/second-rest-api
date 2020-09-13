package misrraimsp.technest_rest_api.util.exception;

public class EntityNotFoundByIdException extends IllegalArgumentException {

    private final String className;
    private final Long entityId;

    public EntityNotFoundByIdException(Long entityId, String className) {
        super("Entity of class " + className + " not found by id=" + entityId);
        this.className = className;
        this.entityId = entityId;
    }

    public String getClassName() {
        return className;
    }

    public Long getEntityId() {
        return entityId;
    }
}
