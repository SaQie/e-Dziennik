package pl.edziennik.common.dto.grade.allsubjects;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.StudentId;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsGradesHeaderDto(

        StudentId studentId,
        FullName fullName,
        List<StudentAssignedSubjectsDto> subjects

) {

    public StudentAllSubjectsGradesHeaderDto(StudentId studentId, FullName fullName) {
        this(studentId, fullName, new ArrayList<>());
    }

    public StudentAllSubjectsGradesHeaderDto(StudentAllSubjectsGradesHeaderDto dto, List<StudentAssignedSubjectsDto> subjects) {
        this(dto.studentId, dto.fullName, subjects);
    }
}
