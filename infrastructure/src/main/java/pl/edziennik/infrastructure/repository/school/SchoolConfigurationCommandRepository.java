package pl.edziennik.infrastructure.repository.school;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.domain.school.SchoolConfiguration;

@RepositoryDefinition(domainClass = SchoolConfiguration.class, idClass = SchoolConfigurationId.class)
public interface SchoolConfigurationCommandRepository {

    SchoolConfiguration getSchoolConfigurationBySchoolConfigurationId(SchoolConfigurationId schoolConfigurationId);

    SchoolConfiguration save(SchoolConfiguration schoolConfiguration);
}
