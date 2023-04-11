package pl.edziennik.eDziennik.domain.subject.domain.wrapper;

public record SubjectId(
        Long id
) {
    public static SubjectId wrap(Long id) {
        return new SubjectId(id);
    }
}
