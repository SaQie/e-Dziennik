package pl.edziennik.infrastructure.spring.script;

import pl.edziennik.common.valueobject.vo.ScriptResult;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

/**
 * Record used for store result of groovy script
 */
public record GroovyScriptExecResult(
        boolean isFinished,
        GroovyScriptStatusId scriptStatusId,
        ScriptResult result,
        BusinessException error) {

}
