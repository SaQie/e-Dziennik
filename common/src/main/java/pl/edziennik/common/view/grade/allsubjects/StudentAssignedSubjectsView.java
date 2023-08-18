package pl.edziennik.common.view.grade.allsubjects;

import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.ArrayList;
import java.util.List;

public record StudentAssignedSubjectsView(

        StudentSubjectId studentSubjectId,
        Name subjectName,
        FullName teacherFullName,
        List<DetailedGradeView> grades

) {

    public StudentAssignedSubjectsView(StudentSubjectId studentSubjectId, Name subjectName, FullName teacherFullName) {
        this(studentSubjectId, subjectName, teacherFullName, new ArrayList<>());
    }

    public StudentAssignedSubjectsView(StudentAssignedSubjectsView dto, List<DetailedGradeView> grades) {
        this(dto.studentSubjectId, dto.subjectName, dto.teacherFullName, grades);
    }
}
