package gov.moonbase.model;

public class User {
    private String id;
    private String lastName;
    private String firstName;
    private String organization;
    private String role;

    public User() {}

    public User(String id, String lastName, String firstName, String organization, String role) {
        setId(id);
        setLastName(lastName);
        setFirstName(firstName);
        setOrganization(organization);
        setRole(role);
    }

    public User(String id) {
        setId(id);
    }

    public String getId () { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getOrganization() { return organization; }
    public String getRole() { return role; }
    public void setId(String id) { this.id = id; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setOrganization(String org) { this.organization = org; }
    public void setRole(String role) { this.role = role; }
}
