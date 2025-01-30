package domain;

public class Entity {
    private long ID;
    private long SerialVersionUID;

    public long GetID () {
        return this.ID;
    }

    public long GetSerialVersionUID() {
        return this.SerialVersionUID;
    }

    public void SetID(long id) {
        this.ID = id;
    }

    public void SetSerialVersionUid(long serialVersionUID) {
        this.SerialVersionUID = serialVersionUID;
    }
}
