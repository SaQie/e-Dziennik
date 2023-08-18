package pl.edziennik.infrastructure.repository.groovy.result;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptResultId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.domain.groovy.GroovyScriptResult;

@RepositoryDefinition(domainClass = GroovyScriptResult.class, idClass = GroovyScriptResultId.class)
public interface GroovyScriptResultQueryRepository {


    @Query("SELECT NEW pl.edziennik.common.view.groovy.GroovyScriptResultView(gs.groovyScriptId,gss.scriptResult,gsstatus.name,gss.execTime) " +
            "FROM GroovyScriptResult gss " +
            "JOIN gss.groovyScript gs " +
            "JOIN gs.groovyScriptStatus gsstatus " +
            "WHERE gs.groovyScriptId = :groovyScriptId ")
    GroovyScriptResultView getGroovyScriptResultView(GroovyScriptId groovyScriptId);

    @Query("SELECT NEW pl.edziennik.common.view.groovy.GroovyScriptResultView(gs.groovyScriptId,gss.scriptResult,gsstatus.name,gss.execTime) " +
            "FROM GroovyScriptResult gss " +
            "JOIN gss.groovyScript gs " +
            "JOIN gs.groovyScriptStatus gsstatus " +
            "JOIN gs.user u " +
            "WHERE u.userId = :userId ")
    Page<GroovyScriptResultView> getGroovyScriptResultView(Pageable pageable, UserId userId);

}
