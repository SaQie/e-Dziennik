package pl.edziennik.application.command.groovy;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.vo.ScriptContent;

/**
 * A command used for execute groovy script
 */
@HandledBy(handler = ExecuteGroovyScriptCommandHandler.class)
public record ExecuteGroovyScriptCommand(

        @Valid @NotNull(message = "${field.empty}") ScriptContent scriptContent

) implements ICommand<OperationResult> {

    public static final String SCRIPT_CONTENT = "scriptContent";

}
