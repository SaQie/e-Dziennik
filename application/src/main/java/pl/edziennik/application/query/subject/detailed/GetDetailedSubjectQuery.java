package pl.edziennik.application.query.subject.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;


/**
 * A query used for getting the detailed subject information
 * <br>
 * <b>Return DTO: {@link DetailedSubjectDto}</b>
 */
@HandledBy(handler = GetDetailedSubjectQueryHandler.class)
public record GetDetailedSubjectQuery(

        @NotNull(message = "${field.empty}") SubjectId subjectId

) implements IQuery<DetailedSubjectDto> {

    public static final String SUBJECT_ID = "subjectId";

}
