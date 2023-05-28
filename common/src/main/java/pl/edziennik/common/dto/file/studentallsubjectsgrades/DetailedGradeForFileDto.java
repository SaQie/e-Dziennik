package pl.edziennik.common.dto.file.studentallsubjectsgrades;

import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.StudentSubjectId;

public record DetailedGradeForFileDto(

        StudentSubjectId studentSubjectId,
        Grade grade

) {


}
