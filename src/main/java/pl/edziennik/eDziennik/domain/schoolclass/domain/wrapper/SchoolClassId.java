package pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper;

public record SchoolClassId(
        Long id
) {


    public static SchoolClassId wrap(Long id) {
        return new SchoolClassId(id);
    }
}
