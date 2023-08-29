package pl.edziennik.application.query.groovy;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;

public interface GroovyQueryDao {

    PageView<GroovyScriptResultView> getGroovyScriptExecResultByUser(Pageable pageable, UserId userId);

    GroovyScriptResultView getGroovyScriptResultView(GroovyScriptId groovyScriptId);

}
