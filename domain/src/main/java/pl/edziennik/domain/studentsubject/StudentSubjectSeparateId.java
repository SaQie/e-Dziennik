package pl.edziennik.domain.studentsubject;


import pl.edziennik.domain.student.StudentId;
import pl.edziennik.domain.subject.SubjectId;

public record StudentSubjectSeparateId(
        StudentId studentId,
        SubjectId subjectId
) {

    public static StudentSubjectSeparateId wrap(StudentId studentId, SubjectId subjectId) {
        return new StudentSubjectSeparateId(studentId, subjectId);
    }

}
