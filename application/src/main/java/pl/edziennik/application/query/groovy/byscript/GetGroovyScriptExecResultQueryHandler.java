package pl.edziennik.application.query.groovy.byscript;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.groovy.GroovyScriptResultDto;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetGroovyScriptExecResultQueryHandler implements IQueryHandler<GetGroovyScriptExecResultQuery, GroovyScriptResultDto> {

    private final GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;
    private final ResourceCreator res;


    @Override
    public GroovyScriptResultDto handle(GetGroovyScriptExecResultQuery query) {
        GroovyScriptResultDto dto = groovyScriptResultQueryRepository.getGroovyScriptResultDto(query.groovyScriptId());

        if (dto == null) {
            throw new BusinessException(res.notFoundError(GetGroovyScriptExecResultQuery.GROOVY_SCRIPT_ID, query.groovyScriptId()));
        }

        return dto;
    }
}
