package pl.edziennik.infrastructure.repository.director;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.domain.director.Director;

import java.util.Optional;

@RepositoryDefinition(domainClass = Director.class, idClass = DirectorId.class)
public interface DirectorCommandRepository {

    Director save(Director director);

    Optional<Director> findById(DirectorId directorId);

    Director getByDirectorId(DirectorId directorId);
}
