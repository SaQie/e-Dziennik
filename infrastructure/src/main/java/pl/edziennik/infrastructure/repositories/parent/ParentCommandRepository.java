package pl.edziennik.infrastructure.repositories.parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.parent.Parent;

import java.util.Optional;

@RepositoryDefinition(domainClass = Parent.class, idClass = ParentId.class)
public interface ParentCommandRepository {


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Parent p " +
            "JOIN p.student s " +
            "WHERE p.id = :#{#parentId.id()}")
    boolean hasAlreadyAssignedStudent(ParentId parentId);

    Parent getReferenceById(ParentId parentId);

    Optional<Parent> findById(ParentId parentId);
}
