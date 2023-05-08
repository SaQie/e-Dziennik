package pl.edziennik.infrastructure.command.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.parent.ParentId;

@Repository
public interface ParentCommandRepository extends JpaRepository<Parent, ParentId> {


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Parent p " +
            "JOIN p.student s " +
            "WHERE p.id = :#{#parentId.id()}")
    boolean hasAlreadyAssignedStudent(ParentId parentId);
}
