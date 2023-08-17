package pl.edziennik.web.query.groovy;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.groovy.byscript.GetGroovyScriptExecResultQuery;
import pl.edziennik.application.query.groovy.byuser.GetGroovyScriptExecResultByUserQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.groovy.GroovyScriptResultDto;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.UserId;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scripts/results")
public class GroovyScriptExecutorQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{groovyScriptId}")
    public GroovyScriptResultDto getGroovyScriptExecResultByScript(@PathVariable GroovyScriptId groovyScriptId) {
        GetGroovyScriptExecResultQuery query = new GetGroovyScriptExecResultQuery(groovyScriptId);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/users/{userId}")
    public PageDto<GroovyScriptResultDto> getGroovyScriptExecResultByUser(Pageable pageable, @PathVariable UserId userId) {
        GetGroovyScriptExecResultByUserQuery query = new GetGroovyScriptExecResultByUserQuery(userId, pageable);

        return dispatcher.dispatch(query);
    }

}
