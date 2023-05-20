package pl.edziennik.common.dto.grade;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

import java.util.Collections;
import java.util.List;

public record StudentGradesBySubjectDto(

        StudentSubjectId studentSubjectId,
        StudentId studentId,
        FullName fullName,
        List<DetailedGradeDto> grades

) {

    public StudentGradesBySubjectDto(StudentSubjectId studentSubjectId, StudentId studentId, FullName fullName) {
        this(studentSubjectId, studentId, fullName, Collections.emptyList());
    }

    public StudentGradesBySubjectDto(StudentGradesBySubjectDto dto, List<DetailedGradeDto> grades) {
        this(dto.studentSubjectId, dto.studentId, dto.fullName, grades);
    }
}
