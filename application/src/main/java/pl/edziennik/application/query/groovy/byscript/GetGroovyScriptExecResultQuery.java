package pl.edziennik.application.query.groovy.byscript;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.common.valueobject.id.GroovyScriptId;


/**
 * A query used for getting the groovy script exec result
 * <br>
 * <b>Return DTO: {@link GroovyScriptResultView}</b>
 */
@HandledBy(handler = GetGroovyScriptExecResultQueryHandler.class)
public record GetGroovyScriptExecResultQuery(

        @NotNull(message = "${field.empty}") GroovyScriptId groovyScriptId

) implements IQuery<GroovyScriptResultView> {

    public static final String GROOVY_SCRIPT_ID = "groovyScriptId";


}
