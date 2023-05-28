package pl.edziennik.common.dto.file.studentallsubjectsgrades;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsSummaryForFileDto(

        StudentSubjectId studentSubjectId,
        Name subjectName,
        FullName teacherFullName,
        List<DetailedGradeForFileDto> grades

) {

    public StudentAllSubjectsSummaryForFileDto(StudentSubjectId studentSubjectId, Name subjectName, FullName teacherFullName) {
        this(studentSubjectId, subjectName, teacherFullName, new ArrayList<>());
    }

    public StudentAllSubjectsSummaryForFileDto(StudentAllSubjectsSummaryForFileDto dto, List<DetailedGradeForFileDto> grades) {
        this(dto.studentSubjectId, dto.subjectName, dto.teacherFullName, grades);
    }
}
