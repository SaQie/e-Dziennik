package pl.edziennik.web.query.groovy;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.query.groovy.GroovyQueryDao;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scripts/results")
public class GroovyScriptExecutorQueryController {

    private final GroovyQueryDao dao;

    @GetMapping("/{groovyScriptId}")
    public GroovyScriptResultView getGroovyScriptExecResultByScript(@PathVariable GroovyScriptId groovyScriptId) {
        return dao.getGroovyScriptResultView(groovyScriptId);
    }

    @GetMapping("/users/{userId}")
    public PageView<GroovyScriptResultView> getGroovyScriptExecResultByUser(Pageable pageable, @PathVariable UserId userId) {
        return dao.getGroovyScriptExecResultByUser(pageable, userId);
    }

}
