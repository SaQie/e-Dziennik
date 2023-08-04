package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;

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
        return parent.student() != null;
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
        database.put(parent.parentId(), parent);
        return database.get(parent.parentId());
    }

    @Override
    public List<Parent> getParentsByUserIds(List<UserId> userIds) {
        return database.values().stream()
                .filter(item -> userIds.contains(item.user().userId()))
                .toList();
    }

    @Override
    public void deleteAll(Iterable<Parent> parentIds) {
        database.clear();
    }

    @Override
    public boolean isParentAccountNotActive(ParentId parentId) {
        Parent parent = database.get(parentId);
        if (parent == null) {
            return true;
        }
        return !parent.user().isActive();
    }

}
