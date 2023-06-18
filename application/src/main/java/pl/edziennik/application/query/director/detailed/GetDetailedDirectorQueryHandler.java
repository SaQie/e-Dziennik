package pl.edziennik.application.query.director.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.director.DetailedDirectorDto;
import pl.edziennik.infrastructure.repository.director.DirectorQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedDirectorQueryHandler implements IQueryHandler<GetDetailedDirectorQuery, DetailedDirectorDto> {

    private final DirectorQueryRepository directorQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedDirectorDto handle(GetDetailedDirectorQuery query) {
        DetailedDirectorDto dto = directorQueryRepository.getDirector(query.directorId());

        if (dto == null) {
            throw new BusinessException(res.notFoundError(GetDetailedDirectorQuery.DIRECTOR_ID, query.directorId()));
        }

        return dto;
    }
}
