package pl.edziennik.infrastructure.repository.groovy.status;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.domain.groovy.GroovyScriptStatus;

@RepositoryDefinition(domainClass = GroovyScriptStatus.class, idClass = GroovyScriptStatusId.class)
public interface GroovyScriptStatusQueryRepository {

    GroovyScriptStatus getByGroovyScriptStatusId(GroovyScriptStatusId id);

}
