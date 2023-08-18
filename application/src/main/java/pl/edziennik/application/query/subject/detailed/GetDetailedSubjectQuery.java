package pl.edziennik.application.query.subject.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.common.valueobject.id.SubjectId;


/**
 * A query used for getting the detailed subject information
 * <br>
 * <b>Return DTO: {@link DetailedSubjectView}</b>
 */
@HandledBy(handler = GetDetailedSubjectQueryHandler.class)
public record GetDetailedSubjectQuery(

        @NotNull(message = "${field.empty}") SubjectId subjectId

) implements IQuery<DetailedSubjectView> {

    public static final String SUBJECT_ID = "subjectId";

}
