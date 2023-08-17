package pl.edziennik.application.query.groovy.byuser;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.groovy.GroovyScriptResultDto;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;

@Component
@AllArgsConstructor
class GetGroovyScriptExecResultByUserQueryHandler implements IQueryHandler<GetGroovyScriptExecResultByUserQuery, PageDto<GroovyScriptResultDto>> {

    private final GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;

    @Override
    public PageDto<GroovyScriptResultDto> handle(GetGroovyScriptExecResultByUserQuery query) {
        Page<GroovyScriptResultDto> resultDto = groovyScriptResultQueryRepository.getGroovyScriptResultDto(query.pageable(), query.userId());

        return PageDto.fromPage(resultDto);
    }
}
