package pl.edziennik.common.view.file.studentallsubjectsgrades;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.ArrayList;
import java.util.List;

public record StudentAllSubjectsSummaryForFileView(

        StudentSubjectId studentSubjectId,
        Name subjectName,
        FullName teacherFullName,
        List<DetailedGradeForFileView> grades

) {

    public StudentAllSubjectsSummaryForFileView(StudentSubjectId studentSubjectId, Name subjectName, FullName teacherFullName) {
        this(studentSubjectId, subjectName, teacherFullName, new ArrayList<>());
    }

    public StudentAllSubjectsSummaryForFileView(StudentAllSubjectsSummaryForFileView dto, List<DetailedGradeForFileView> grades) {
        this(dto.studentSubjectId, dto.subjectName, dto.teacherFullName, grades);
    }
}
