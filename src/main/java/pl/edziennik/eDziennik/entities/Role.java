package pl.edziennik.eDziennik.entities;

public enum Role {

    ROLE_ADMIN("Administrator"),
    ROLE_MODERATOR("Moderator"),
    ROLE_TEACHER("Nauczyciel");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
