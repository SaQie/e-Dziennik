package pl.edziennik.common.valueobject.id;


public record StudentSubjectSeparateId(
        StudentId studentId,
        SubjectId subjectId
) {

    public static StudentSubjectSeparateId wrap(StudentId studentId, SubjectId subjectId) {
        return new StudentSubjectSeparateId(studentId, subjectId);
    }

}
