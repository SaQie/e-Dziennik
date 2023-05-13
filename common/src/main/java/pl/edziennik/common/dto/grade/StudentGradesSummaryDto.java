package pl.edziennik.common.dto.grade;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.StudentId;

import java.util.Collections;
import java.util.List;

public record StudentGradesSummaryDto(

        StudentId studentId,
        FullName fullName,
        List<DetailedStudentSubjectGradeDto> grades

) {

    public StudentGradesSummaryDto(StudentId studentId, FullName fullName) {
        this(studentId, fullName, Collections.emptyList());
    }

    public StudentGradesSummaryDto(StudentGradesSummaryDto dto, List<DetailedStudentSubjectGradeDto> grades) {
        this(dto.studentId, dto.fullName, grades);
    }
}
