package pl.edziennik.eDziennik.domain.grade.domain.wrapper;

public record GradeId(
        Long id
) {
    public static GradeId wrap(Long id){
        return new GradeId(id);
    }
}
