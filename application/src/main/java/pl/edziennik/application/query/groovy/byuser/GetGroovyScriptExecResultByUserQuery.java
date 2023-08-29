package pl.edziennik.application.query.groovy.byuser;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.common.valueobject.id.UserId;


/**
 * A query used for getting the groovy script exec results for specific user
 * <br>
 * <b>Return DTO: {@link GroovyScriptResultView}</b>
 */
@HandledBy(handler = GetGroovyScriptExecResultByUserQueryHandler.class)
public record GetGroovyScriptExecResultByUserQuery(

        @NotNull(message = "${field.empty}") UserId userId,
        Pageable pageable

) implements IQuery<PageView<GroovyScriptResultView>> {

    public static final String USER_ID = "userId";


}
