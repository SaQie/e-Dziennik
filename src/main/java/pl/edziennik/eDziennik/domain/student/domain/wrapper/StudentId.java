package pl.edziennik.eDziennik.domain.student.domain.wrapper;

public record StudentId(
        Long id
) {
    public static StudentId wrap(Long id) {
        return new StudentId(id);
    }
}
