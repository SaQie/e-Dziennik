package pl.edziennik.infrastructure.repository.groovy;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.domain.groovy.GroovyScript;

@RepositoryDefinition(domainClass = GroovyScript.class, idClass = GroovyScriptId.class)
public interface GroovyScriptCommandRepository {

    GroovyScript save(GroovyScript groovyScript);

    void flush();

}
