package pl.edziennik.infrastructure.spring.script;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.SyntaxException;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.common.valueobject.vo.ScriptContent;
import pl.edziennik.common.valueobject.vo.ScriptResult;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * A class used for executing groovy script
 */
@Slf4j
public class GroovyShellExecutor {

    private final GroovyShell groovyShell;
    private final ResourceCreator res;
    private final ObjectMapper objectMapper;

    protected GroovyShellExecutor(GroovyShell groovyShell, ResourceCreator resourceCreator) {
        this.groovyShell = groovyShell;
        this.res = resourceCreator;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Executes groovy script
     */
    public GroovyScriptExecResult execute(GroovyScriptId groovyScriptId, ScriptContent scriptContent) {
        return getScriptResult(groovyScriptId, scriptContent);
    }

    private GroovyScriptExecResult getScriptResult(GroovyScriptId groovyScriptId, ScriptContent scriptContent) {
        try {
            // Run script and save result
            Object result = runScript(scriptContent);
            ScriptResult scriptResult = ScriptResult.of(result.toString());

            return new GroovyScriptExecResult(true, GroovyScriptStatusId.PredefinedRow.SUCCESS, scriptResult, null);

        } catch (TimeoutException e) {
            // If there were a timeout
            log.error("Failed during executing groovy script: {}", e.getMessage());

            return mapResultUsingBusinessException(groovyScriptId, e, e.getMessage());

        } catch (SyntaxException e) {
            // If there were syntax error
            return mapResultUsingBusinessException(groovyScriptId, e, e.getCause() == null ? e.getMessage() : e.getCause().getMessage());

        } catch (MultipleCompilationErrorsException e) {
            // If there were multiple compilation errors
            log.error("Failed during executing groovy script: {}", e.getMessage());

            ErrorCollector errorCollector = e.getErrorCollector();
            List<? extends Message> errors = errorCollector.getErrors();
            List<String> errorList = errors.stream()
                    .filter(error -> error instanceof ExceptionMessage)
                    .map(error -> (ExceptionMessage) error)
                    .map(error -> error.getCause().getCause() != null ? error.getCause().getCause().getMessage() : error.getCause().getMessage())
                    .toList();

            List<String> errorListSecond = errors.stream()
                    .filter(error -> error instanceof SyntaxErrorMessage)
                    .map(error -> (SyntaxErrorMessage) error)
                    .map(error -> error.getCause().getCause() != null ? error.getCause().getCause().getMessage() : error.getCause().getMessage())
                    .toList();

            List<String> combinedErrorList = new ArrayList<>(errorList);
            combinedErrorList.addAll(errorListSecond);

            String parsedError = String.join(", ", combinedErrorList);


            return mapResultUsingBusinessException(groovyScriptId, e, parsedError);

        }
    }

    /**
     * Method to wrap exception into BusinessException
     */
    private GroovyScriptExecResult mapResultUsingBusinessException(GroovyScriptId groovyScriptId, Exception e, String message) {
        ValidationError validationError = new ValidationError("scriptContent",
                res.of("groovy.script.executing.error", groovyScriptId.id(),message),
                ErrorCode.GROOVY_SCRIPT_ERROR);
        BusinessException exception = new BusinessException(validationError, e);

        return new GroovyScriptExecResult(false, GroovyScriptStatusId.PredefinedRow.ERROR, null, exception);
    }


    /**
     * Run groovy script (with check that script returns ScriptResult object)
     */
    private Object runScript(ScriptContent scriptContent) throws TimeoutException, SyntaxException {
        Object result = groovyShell.evaluate(scriptContent.value());
        if (!(result instanceof ScriptResult)) {
            throw new SyntaxException("Groovy script must return ScriptResult object ! Usage: return new ScriptResult(<put value>)", 0, 0);
        }
        return result;
    }

}
