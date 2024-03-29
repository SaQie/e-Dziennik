package pl.edziennik.infrastructure.repository.parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.parent.Parent;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Parent.class, idClass = ParentId.class)
public interface ParentCommandRepository {


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Parent p " +
            "JOIN p.student s " +
            "WHERE p.parentId = :parentId")
    boolean hasAlreadyAssignedStudent(ParentId parentId);

    Parent getReferenceById(ParentId parentId);

    Optional<Parent> findById(ParentId parentId);

    Parent save(Parent parent);

    @Query("SELECT p FROM Parent p where p.user.userId IN (:userIds)")
    List<Parent> getParentsByUserIds(List<UserId> userIds);

    void deleteAll(Iterable<Parent> parentIds);

    @Query("SELECT CASE WHEN COUNT(p) > 1 THEN TRUE ELSE FALSE END " +
            "FROM Parent p " +
            "JOIN p.user u " +
            "WHERE p.parentId = :parentId " +
            "AND u.isActive = false")
    boolean isParentAccountNotActive(ParentId parentId);
}
