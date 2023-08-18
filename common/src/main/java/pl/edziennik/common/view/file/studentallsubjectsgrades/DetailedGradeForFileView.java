package pl.edziennik.common.view.file.studentallsubjectsgrades;

import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

public record DetailedGradeForFileView(

        StudentSubjectId studentSubjectId,
        Grade grade

) {


}
