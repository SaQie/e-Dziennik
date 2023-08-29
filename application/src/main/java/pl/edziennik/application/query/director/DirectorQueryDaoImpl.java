package pl.edziennik.application.query.director;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.view.director.DetailedDirectorView;
import pl.edziennik.infrastructure.repository.director.DirectorQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
class DirectorQueryDaoImpl implements DirectorQueryDao {

    private final ResourceCreator res;
    private final DirectorQueryRepository directorQueryRepository;


    @Override
    public DetailedDirectorView getDetailedDirectorView(DirectorId directorId) {
        DetailedDirectorView view = directorQueryRepository.getDirectorView(directorId);

        requireNonNull(view, directorId);

        return view;
    }

    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}
