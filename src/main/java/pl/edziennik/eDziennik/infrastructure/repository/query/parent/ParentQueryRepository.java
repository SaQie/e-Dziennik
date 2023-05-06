package pl.edziennik.eDziennik.infrastructure.repository.query.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;

@Repository
public interface ParentQueryRepository extends JpaRepository<Parent, ParentId> {
}
