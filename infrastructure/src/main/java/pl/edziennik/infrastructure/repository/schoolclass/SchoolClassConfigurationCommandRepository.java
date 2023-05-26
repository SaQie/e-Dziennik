package pl.edziennik.infrastructure.repository.schoolclass;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolClassConfigurationId;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;

@RepositoryDefinition(domainClass = SchoolClassConfiguration.class, idClass = SchoolClassConfigurationId.class)
public interface SchoolClassConfigurationCommandRepository {

    SchoolClassConfiguration getSchoolClassConfigurationBySchoolClassConfigurationId(SchoolClassConfigurationId schoolClassConfigurationId);

    SchoolClassConfiguration save(SchoolClassConfiguration schoolClassConfiguration);

}
