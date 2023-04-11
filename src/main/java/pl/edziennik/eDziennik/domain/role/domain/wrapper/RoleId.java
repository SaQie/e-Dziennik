package pl.edziennik.eDziennik.domain.role.domain.wrapper;

public record RoleId(
        Long id
) {
    public static RoleId wrap(Long id) {
        return new RoleId(id);
    }
}
