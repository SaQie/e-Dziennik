package pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper;

public record StudentSubjectId(
        Long id
) {

    public static StudentSubjectId wrap(Long id){
        return new StudentSubjectId(id);
    }
}
