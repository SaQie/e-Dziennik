package pl.edziennik.common.view.groovy;

import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.vo.GroovyScriptExecTime;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.vo.ScriptResult;

public record GroovyScriptResultView(
        GroovyScriptId groovyScriptId,
        ScriptResult scriptResult,
        Name status,
        GroovyScriptExecTime execTime
) {
}
