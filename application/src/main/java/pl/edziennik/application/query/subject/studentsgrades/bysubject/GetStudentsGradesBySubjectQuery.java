package pl.edziennik.application.query.subject.studentsgrades.bysubject;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;

import java.util.List;

@HandledBy(handler = GetStudentsGradesBySubjectQueryHandler.class)
public record GetStudentsGradesBySubjectQuery(

        @NotNull SubjectId subjectId

) implements IQuery<List<StudentGradesBySubjectDto>> {

    public static final String SUBJECT_ID = "subjectId";

}
