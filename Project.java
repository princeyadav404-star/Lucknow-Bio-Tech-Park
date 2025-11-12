package model;

public class Project {
    private int id;
    private String name;
    private String description;
    private String leadScientist;

    public Project() {}

    public Project(int id, String name, String description, String leadScientist) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leadScientist = leadScientist;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLeadScientist() { return leadScientist; }
    public void setLeadScientist(String leadScientist) { this.leadScientist = leadScientist; }
}
