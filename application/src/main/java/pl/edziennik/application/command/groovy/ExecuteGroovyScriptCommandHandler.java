package pl.edziennik.application.command.groovy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.domain.groovy.GroovyScript;
import pl.edziennik.domain.groovy.GroovyScriptResult;
import pl.edziennik.domain.groovy.GroovyScriptStatus;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.groovy.GroovyScriptCommandRepository;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultCommandRepository;
import pl.edziennik.infrastructure.repository.groovy.status.GroovyScriptStatusQueryRepository;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.spring.script.GroovyScriptExecResult;
import pl.edziennik.infrastructure.spring.script.GroovyShellExecutor;

@Component
@AllArgsConstructor
@Slf4j
class ExecuteGroovyScriptCommandHandler implements ICommandHandler<ExecuteGroovyScriptCommand, OperationResult> {

    private final GroovyShellExecutor groovyShellExecutor;
    private final UserQueryRepository userQueryRepository;
    private final GroovyScriptStatusQueryRepository groovyScriptStatusQueryRepository;
    private final GroovyScriptCommandRepository groovyScriptCommandRepository;
    private final GroovyScriptResultCommandRepository groovyScriptResultCommandRepository;

    @Override
    @Transactional(noRollbackFor = BusinessException.class)
    public OperationResult handle(ExecuteGroovyScriptCommand command) {
        // 1. Get required information
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userQueryRepository.getByUsername(Username.of(name));
        GroovyScriptStatus scriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptStatusId(
                GroovyScriptStatusId.PredefinedRow.EXECUTING);

        GroovyScript groovyScript = GroovyScript.builder()
                .user(user)
                .scriptStatus(scriptStatus)
                .scriptContent(command.scriptContent())
                .build();

        // 2. Save groovy script execution attempt (executing status)
        GroovyScript script = groovyScriptCommandRepository.save(groovyScript);
        groovyScriptCommandRepository.flush();

        // 3. execute groovy script
        GroovyScriptExecResult scriptExecResult = groovyShellExecutor.execute(command.scriptContent());

        if (scriptExecResult.isFinished()) {
            // If everything was success
            changeStatus(scriptExecResult.scriptStatusId(), script);

            GroovyScriptResult groovyScriptResult = GroovyScriptResult.builder()
                    .groovyScript(script)
                    .scriptResult(scriptExecResult.result())
                    .build();

            groovyScriptResultCommandRepository.save(groovyScriptResult);

        } else {
            // If something went wrong
            changeStatus(scriptExecResult.scriptStatusId(), script);

            throw scriptExecResult.error();
        }

        return OperationResult.success(script.groovyScriptId());
    }

    /**
     * Method used for changing groovy script status
     */
    private void changeStatus(GroovyScriptStatusId statusId, GroovyScript groovyScript) {
        GroovyScriptStatus scriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptStatusId(
                statusId);
        groovyScript.changeStatus(scriptStatus);
    }

}
