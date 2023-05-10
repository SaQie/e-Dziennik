package pl.edziennik.infrastructure.repositories.schoolclass;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.domain.schoolclass.SchoolClass;

@RepositoryDefinition(domainClass = SchoolClass.class, idClass = SchoolClassId.class)
public interface SchoolClassQueryRepository {

    SchoolClass getReferenceById(SchoolClassId schoolClassId);


}
