package pl.edziennik.application.query.groovy;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class GroovyQueryDaoImpl implements GroovyQueryDao {

    private final GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;
    private final ResourceCreator res;

    @Override
    public PageView<GroovyScriptResultView> getGroovyScriptExecResultByUser(Pageable pageable, UserId userId) {
        return PageView.fromPage(groovyScriptResultQueryRepository.getGroovyScriptResultView(pageable, userId));
    }

    @Override
    public GroovyScriptResultView getGroovyScriptResultView(GroovyScriptId groovyScriptId) {
        GroovyScriptResultView view = groovyScriptResultQueryRepository.getGroovyScriptResultView(groovyScriptId);

        requireNonNull(view, groovyScriptId);

        return view;
    }


    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}
