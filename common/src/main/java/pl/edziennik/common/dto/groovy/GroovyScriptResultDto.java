package pl.edziennik.common.dto.groovy;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.ScriptResult;
import pl.edziennik.common.valueobject.id.GroovyScriptId;

public record GroovyScriptResultDto(
        GroovyScriptId groovyScriptId,
        ScriptResult scriptResult,
        Name status,
        Long execTime
) {
}
