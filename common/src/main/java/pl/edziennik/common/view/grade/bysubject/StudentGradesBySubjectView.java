package pl.edziennik.common.view.grade.bysubject;

import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.Collections;
import java.util.List;

public record StudentGradesBySubjectView(

        StudentSubjectId studentSubjectId,
        StudentId studentId,
        FullName fullName,
        List<DetailedGradeView> grades

) {

    public StudentGradesBySubjectView(StudentSubjectId studentSubjectId, StudentId studentId, FullName fullName) {
        this(studentSubjectId, studentId, fullName, Collections.emptyList());
    }

    public StudentGradesBySubjectView(StudentGradesBySubjectView dto, List<DetailedGradeView> grades) {
        this(dto.studentSubjectId, dto.studentId, dto.fullName, grades);
    }
}
