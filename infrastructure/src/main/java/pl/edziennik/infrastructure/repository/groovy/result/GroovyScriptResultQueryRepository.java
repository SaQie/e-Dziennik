package pl.edziennik.infrastructure.repository.groovy.result;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptResultId;
import pl.edziennik.domain.groovy.GroovyScriptResult;

@RepositoryDefinition(domainClass = GroovyScriptResult.class, idClass = GroovyScriptResultId.class)
public interface GroovyScriptResultQueryRepository {



}
