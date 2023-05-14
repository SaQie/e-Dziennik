package pl.edziennik.application.query.teacher.detailedsubject;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.teacher.TeacherDetailedSubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

@HandledBy(handler = GetTeacherDetailedSubjectQueryHandler.class)
@ValidatedBy(validator = GetTeacherDetailedSubjectQueryValidator.class)
public record GetTeacherDetailedSubjectQuery(

        TeacherId teacherId,
        SubjectId subjectId

) implements IQuery<TeacherDetailedSubjectDto> {

    public static final String TEACHER_ID = "teacherId";
    public static final String SUBJECT_ID = "subjectId";

}
