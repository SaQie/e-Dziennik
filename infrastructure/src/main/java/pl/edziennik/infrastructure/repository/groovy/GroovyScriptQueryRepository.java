package pl.edziennik.infrastructure.repository.groovy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.domain.groovy.GroovyScript;

import java.util.Optional;

@RepositoryDefinition(domainClass = GroovyScript.class, idClass = GroovyScriptId.class)
public interface GroovyScriptQueryRepository {

    Optional<GroovyScript> findById(GroovyScriptId groovyScriptId);

    GroovyScript getById(GroovyScriptId groovyScriptId);

    @Query("SELECT gs FROM GroovyScript gs JOIN gs.groovyScriptStatus gss WHERE gs.groovyScriptId = :groovyScriptId")
    GroovyScript getByIdWithStatus(GroovyScriptId groovyScriptId);

}
