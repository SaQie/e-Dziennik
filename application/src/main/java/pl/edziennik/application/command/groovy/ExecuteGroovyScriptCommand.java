package pl.edziennik.application.command.groovy;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.vo.ScriptContent;

/**
 * A command used for execute groovy script
 */
@Handler(handler = ExecuteGroovyScriptCommandHandler.class)
public record ExecuteGroovyScriptCommand(

        @Valid @NotNull(message = "${field.empty}") ScriptContent scriptContent

) implements Command {

    public static final String SCRIPT_CONTENT = "scriptContent";

}
