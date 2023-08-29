package pl.edziennik.application.query.groovy.byuser;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetGroovyScriptExecResultByUserQueryHandler implements IQueryHandler<GetGroovyScriptExecResultByUserQuery, PageView<GroovyScriptResultView>> {

    private final ResourceCreator res;
    private final GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;

    @Override
    public PageView<GroovyScriptResultView> handle(GetGroovyScriptExecResultByUserQuery query) {
        Page<GroovyScriptResultView> view = groovyScriptResultQueryRepository.getGroovyScriptResultView(query.pageable(), query.userId());

        return PageView.fromPage(view);
    }


}
