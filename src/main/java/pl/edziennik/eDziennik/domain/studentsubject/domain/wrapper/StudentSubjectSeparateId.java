package pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper;

import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

public record StudentSubjectSeparateId(
        StudentId studentId,
        SubjectId subjectId
) {

    public static StudentSubjectSeparateId wrap(StudentId studentId, SubjectId subjectId) {
        return new StudentSubjectSeparateId(studentId, subjectId);
    }

}
