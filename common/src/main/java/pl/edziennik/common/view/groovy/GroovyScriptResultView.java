package pl.edziennik.common.view.groovy;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.ScriptResult;
import pl.edziennik.common.valueobject.id.GroovyScriptId;

public record GroovyScriptResultView(
        GroovyScriptId groovyScriptId,
        ScriptResult scriptResult,
        Name status,
        Long execTime
) {
}
