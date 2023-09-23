package pl.edziennik.application.command.groovy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.vo.ScriptContent;

/**
 * A command used for execute groovy script
 */
@Handler(handler = ExecuteGroovyScriptCommandHandler.class)
public record ExecuteGroovyScriptCommand(

        @JsonIgnore GroovyScriptId groovyScriptId,

        @Schema(description = "Groovy script code")
        @Valid @NotNull(message = "${field.empty}") ScriptContent scriptContent

) implements Command {

    public static final String SCRIPT_CONTENT = "scriptContent";
    public static final String GROOVY_SCRIPT_ID = "groovyScriptId";


    @JsonCreator
    public ExecuteGroovyScriptCommand(ScriptContent scriptContent) {
        this(GroovyScriptId.create(), scriptContent);
    }
}
