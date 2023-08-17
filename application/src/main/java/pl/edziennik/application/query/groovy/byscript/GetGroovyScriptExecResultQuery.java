package pl.edziennik.application.query.groovy.byscript;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.groovy.GroovyScriptResultDto;
import pl.edziennik.common.valueobject.id.GroovyScriptId;


/**
 * A query used for getting the groovy script exec result
 * <br>
 * <b>Return DTO: {@link GroovyScriptResultDto}</b>
 */
@HandledBy(handler = GetGroovyScriptExecResultQueryHandler.class)
public record GetGroovyScriptExecResultQuery(

        @NotNull(message = "${field.empty}") GroovyScriptId groovyScriptId

) implements IQuery<GroovyScriptResultDto> {

    public static final String GROOVY_SCRIPT_ID = "groovyScriptId";


}
