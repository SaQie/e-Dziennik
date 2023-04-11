package pl.edziennik.eDziennik.domain.teacher.domain.wrapper;

public record TeacherId(
        Long id
) {
    public static TeacherId wrap(Long id) {
        return new TeacherId(id);
    }
}
