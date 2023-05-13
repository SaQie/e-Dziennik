package pl.edziennik.common.dto.teacher;

import pl.edziennik.common.dto.grade.StudentGradesSummaryDto;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.util.Collections;
import java.util.List;

public record TeacherDetailedSubjectDto(

        TeacherId teacherId,
        FullName teacherName,
        SubjectId subjectId,
        Name subjectName,
        List<StudentGradesSummaryDto> subjectSummary

) {

    public TeacherDetailedSubjectDto(TeacherId teacherId, FullName teacherName, SubjectId subjectId, Name subjectName) {
        this(teacherId, teacherName, subjectId, subjectName, Collections.emptyList());
    }

    public TeacherDetailedSubjectDto(TeacherDetailedSubjectDto dto, List<StudentGradesSummaryDto> gradesSummaryDtos) {
        this(dto.teacherId, dto.teacherName, dto.subjectId, dto.subjectName, gradesSummaryDtos);
    }

}
