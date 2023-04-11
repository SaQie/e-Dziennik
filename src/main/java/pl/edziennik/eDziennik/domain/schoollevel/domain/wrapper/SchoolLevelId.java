package pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper;

public record SchoolLevelId(
        Long id
) {
    public static SchoolLevelId wrap(Long id) {
        return new SchoolLevelId(id);
    }

}
