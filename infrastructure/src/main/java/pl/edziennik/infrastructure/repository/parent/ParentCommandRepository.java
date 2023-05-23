package pl.edziennik.infrastructure.repository.parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.parent.Parent;

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

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Parent p " +
            "JOIN p.user u " +
            "WHERE u.email = :email")
    boolean existsByEmail(Email email);


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Parent p " +
            "JOIN p.user u " +
            "WHERE u.username = :username")
    boolean existsByUsername(Username username);
}
