package pl.edziennik.application.query.director.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.director.DetailedDirectorView;
import pl.edziennik.infrastructure.repository.director.DirectorQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedDirectorQueryHandler implements IQueryHandler<GetDetailedDirectorQuery, DetailedDirectorView> {

    private final DirectorQueryRepository directorQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedDirectorView handle(GetDetailedDirectorQuery query) {
        DetailedDirectorView view = directorQueryRepository.getDirectorView(query.directorId());

        if (view == null) {
            throw new BusinessException(res.notFoundError(GetDetailedDirectorQuery.DIRECTOR_ID, query.directorId()));
        }

        return view;
    }
}
