package ro.mpp2025.model;

public interface Identifiable<Tid> {
    Tid getID();
    void setID(Tid id);
}
