package pl.edziennik.common.dto.grade;

import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Weight;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;

public record DetailedStudentSubjectGradeDto(

        StudentId studentId,
        GradeId gradeId,
        Grade grade,
        Weight weight,
        Description description,
        TeacherId teacherId,
        FullName teacherFullName

) {
}
