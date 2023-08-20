package pl.edziennik.common.view.grade.allsubjects;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.id.StudentId;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsGradesHeaderView(

        StudentId studentId,
        FullName fullName,
        List<StudentAssignedSubjectsView> subjects

) {

    public StudentAllSubjectsGradesHeaderView(StudentId studentId, FullName fullName) {
        this(studentId, fullName, new ArrayList<>());
    }

    public StudentAllSubjectsGradesHeaderView(StudentAllSubjectsGradesHeaderView dto, List<StudentAssignedSubjectsView> subjects) {
        this(dto.studentId, dto.fullName, subjects);
    }
}
