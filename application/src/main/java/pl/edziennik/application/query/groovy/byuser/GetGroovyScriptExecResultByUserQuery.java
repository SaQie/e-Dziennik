package pl.edziennik.application.query.groovy.byuser;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.groovy.GroovyScriptResultDto;
import pl.edziennik.common.valueobject.id.UserId;


/**
 * A query used for getting the groovy script exec results for specific user
 * <br>
 * <b>Return DTO: {@link GroovyScriptResultDto}</b>
 */
@HandledBy(handler = GetGroovyScriptExecResultByUserQueryHandler.class)
public record GetGroovyScriptExecResultByUserQuery(

        @NotNull(message = "${field.empty}") UserId userId,
        Pageable pageable

) implements IQuery<PageDto<GroovyScriptResultDto>> {

    public static final String USER_ID = "userId";


}
