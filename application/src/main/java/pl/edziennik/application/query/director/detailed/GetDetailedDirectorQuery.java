package pl.edziennik.application.query.director.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.director.DetailedDirectorView;
import pl.edziennik.common.valueobject.id.DirectorId;

/**
 * A query used for getting the detailed director information
 * <br>
 * <b>Return DTO: {@link DetailedDirectorView}</b>
 */
@HandledBy(handler = GetDetailedDirectorQueryHandler.class)
public record GetDetailedDirectorQuery(

        @NotNull(message = "{director.empty}") DirectorId directorId

) implements IQuery<DetailedDirectorView> {

    public static final String DIRECTOR_ID = "directorId";

}
