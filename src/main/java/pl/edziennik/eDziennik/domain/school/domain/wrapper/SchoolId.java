package pl.edziennik.eDziennik.domain.school.domain.wrapper;

public record SchoolId(
        Long id
) {

    public static SchoolId wrap(Long id) {
        return new SchoolId(id);
    }
}
