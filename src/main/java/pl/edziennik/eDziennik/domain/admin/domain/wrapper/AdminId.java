package pl.edziennik.eDziennik.domain.admin.domain.wrapper;

public record AdminId(
        Long id
) {
    public static AdminId wrap(Long id){
        return new AdminId(id);
    }
}
