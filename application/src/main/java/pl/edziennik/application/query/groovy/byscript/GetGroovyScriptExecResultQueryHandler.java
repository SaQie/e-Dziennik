package pl.edziennik.application.query.groovy.byscript;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetGroovyScriptExecResultQueryHandler implements IQueryHandler<GetGroovyScriptExecResultQuery, GroovyScriptResultView> {

    private final GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;
    private final ResourceCreator res;


    @Override
    public GroovyScriptResultView handle(GetGroovyScriptExecResultQuery query) {
        GroovyScriptResultView view = groovyScriptResultQueryRepository.getGroovyScriptResultView(query.groovyScriptId());

        if (view == null) {
            throw new BusinessException(res.notFoundError(GetGroovyScriptExecResultQuery.GROOVY_SCRIPT_ID, query.groovyScriptId()));
        }

        return view;
    }
}
