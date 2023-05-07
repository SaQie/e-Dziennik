package pl.edziennik.infrastructure.query.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.parent.ParentId;

@Repository
public interface ParentQueryRepository extends JpaRepository<Parent, ParentId> {
}
