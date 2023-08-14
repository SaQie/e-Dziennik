package pl.edziennik.infrastructure.repository.groovy.status;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.domain.groovy.GroovyScriptStatus;

@RepositoryDefinition(domainClass = GroovyScriptStatus.class, idClass = GroovyScriptStatusId.class)
public interface GroovyScriptStatusQueryRepository {

    GroovyScriptStatus getByGroovyScriptStatusId(GroovyScriptStatusId id);

    @Query("SELECT gss FROM GroovyScript gs JOIN gs.groovyScriptStatus gss WHERE gs.groovyScriptId = :groovyScriptId ")
    GroovyScriptStatus getByGroovyScriptId(GroovyScriptId groovyScriptId);

}
