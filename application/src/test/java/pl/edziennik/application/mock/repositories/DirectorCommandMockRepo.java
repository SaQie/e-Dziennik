package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.domain.director.Director;
import pl.edziennik.infrastructure.repository.director.DirectorCommandRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DirectorCommandMockRepo implements DirectorCommandRepository {

    private final Map<DirectorId, Director> database;

    public DirectorCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public Director save(Director director) {
        database.put(director.getDirectorId(), director);
        return database.get(director.getDirectorId());
    }

    @Override
    public Optional<Director> findById(DirectorId directorId) {
        return Optional.ofNullable(database.get(directorId));
    }

    @Override
    public Director getByDirectorId(DirectorId directorId) {
        return database.get(directorId);
    }
}
