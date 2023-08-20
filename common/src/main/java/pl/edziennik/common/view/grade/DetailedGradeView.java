package pl.edziennik.common.view.grade;

import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

public record DetailedGradeView(

        StudentSubjectId studentSubjectId,
        GradeId gradeId,
        Grade grade,
        Weight weight,
        Description description,
        TeacherId teacherId,
        FullName teacherFullName

) {
}
