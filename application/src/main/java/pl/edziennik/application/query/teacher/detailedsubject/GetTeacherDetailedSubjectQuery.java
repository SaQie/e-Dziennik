package pl.edziennik.application.query.teacher.detailedsubject;

import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.teacher.TeacherDetailedSubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

public record GetTeacherDetailedSubjectQuery(

        TeacherId teacherId,
        SubjectId subjectId

) implements IQuery<TeacherDetailedSubjectDto> {
}
