package pl.edziennik.common.dto.grade.allsubjects;

import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.ArrayList;
import java.util.List;

public record StudentAssignedSubjectsDto(

        StudentSubjectId studentSubjectId,
        Name subjectName,
        FullName teacherFullName,
        List<DetailedGradeDto> grades

) {

    public StudentAssignedSubjectsDto(StudentSubjectId studentSubjectId, Name subjectName, FullName teacherFullName) {
        this(studentSubjectId, subjectName, teacherFullName, new ArrayList<>());
    }

    public StudentAssignedSubjectsDto(StudentAssignedSubjectsDto dto, List<DetailedGradeDto> grades) {
        this(dto.studentSubjectId, dto.subjectName, dto.teacherFullName, grades);
    }
}
