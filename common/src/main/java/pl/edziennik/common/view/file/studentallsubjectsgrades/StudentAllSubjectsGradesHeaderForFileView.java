package pl.edziennik.common.view.file.studentallsubjectsgrades;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsGradesHeaderForFileView(

        Name schoolName,
        Name schoolClassName,
        FullName teacherFullName,
        FullName studentFullName,
        List<StudentAllSubjectsSummaryForFileView> subjects

) {

    public StudentAllSubjectsGradesHeaderForFileView(Name schoolName, Name schoolClassName, FullName teacherFullName, FullName studentFullName) {
        this(schoolName, schoolClassName, teacherFullName, studentFullName, new ArrayList<>());
    }

    public StudentAllSubjectsGradesHeaderForFileView(StudentAllSubjectsGradesHeaderForFileView dto, List<StudentAllSubjectsSummaryForFileView> subjects) {
        this(dto.schoolName, dto.schoolClassName, dto.teacherFullName, dto.studentFullName, subjects);
    }
}
