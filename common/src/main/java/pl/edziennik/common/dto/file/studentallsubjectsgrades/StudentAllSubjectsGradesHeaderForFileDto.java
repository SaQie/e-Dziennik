package pl.edziennik.common.dto.file.studentallsubjectsgrades;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsGradesHeaderForFileDto(

        Name schoolName,
        Name schoolClassName,
        FullName teacherFullName,
        FullName studentFullName,
        List<StudentAllSubjectsSummaryForFileDto> subjects

) {

    public StudentAllSubjectsGradesHeaderForFileDto(Name schoolName, Name schoolClassName, FullName teacherFullName, FullName studentFullName) {
        this(schoolName, schoolClassName, teacherFullName, studentFullName, new ArrayList<>());
    }

    public StudentAllSubjectsGradesHeaderForFileDto(StudentAllSubjectsGradesHeaderForFileDto dto, List<StudentAllSubjectsSummaryForFileDto> subjects) {
        this(dto.schoolName, dto.schoolClassName, dto.teacherFullName, dto.studentFullName, subjects);
    }
}
