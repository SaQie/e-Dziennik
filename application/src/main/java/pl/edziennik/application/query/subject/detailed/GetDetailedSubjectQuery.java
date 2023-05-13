package pl.edziennik.application.query.subject.detailed;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;

@HandledBy(handler = GetDetailedSubjectQueryHandler.class)
public record GetDetailedSubjectQuery(

        SubjectId subjectId

) implements IQuery<DetailedSubjectDto> {

    public static final String SUBJECT_ID = "subjectId";

}
