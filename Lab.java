package model;

public class Lab {
    private int id;
    private String name;
    private String equipment;

    public Lab() {}
    public Lab(int id, String name, String equipment) {
        this.id = id; this.name = name; this.equipment = equipment;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
}
