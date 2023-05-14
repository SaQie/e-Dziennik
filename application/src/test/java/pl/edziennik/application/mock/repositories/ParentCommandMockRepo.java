package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.infrastructure.repositories.parent.ParentCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParentCommandMockRepo implements ParentCommandRepository {

    private final Map<ParentId, Parent> database;

    public ParentCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean hasAlreadyAssignedStudent(ParentId parentId) {
        Parent parent = database.get(parentId);
        if (parent == null) {
            return false;
        }
        return parent.getStudent() != null;
    }

    @Override
    public Parent getReferenceById(ParentId parentId) {
        return database.get(parentId);
    }

    @Override
    public Optional<Parent> findById(ParentId parentId) {
        return Optional.ofNullable(database.get(parentId));
    }

    @Override
    public Parent save(Parent parent) {
        database.put(parent.getParentId(), parent);
        return database.get(parent.getParentId());
    }

    @Override
    public boolean existsByEmail(Email email) {
        List<Parent> parents = database.values().stream()
                .filter(item -> item.getUser().getEmail().equals(email))
                .toList();
        return !parents.isEmpty();
    }

    @Override
    public boolean existsByUsername(Username username) {
        List<Parent> parents = database.values().stream()
                .filter(item -> item.getUser().getUsername().equals(username))
                .toList();
        return !parents.isEmpty();
    }
}
