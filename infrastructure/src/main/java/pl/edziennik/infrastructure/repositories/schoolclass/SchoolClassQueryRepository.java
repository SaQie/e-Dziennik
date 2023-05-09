package pl.edziennik.infrastructure.repositories.schoolclass;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassId;

@RepositoryDefinition(domainClass = SchoolClass.class, idClass = SchoolClassId.class)
public interface SchoolClassQueryRepository {

    SchoolClass getReferenceById(SchoolClassId schoolClassId);


}
