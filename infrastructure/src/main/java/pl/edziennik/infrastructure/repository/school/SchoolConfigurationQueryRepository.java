package pl.edziennik.infrastructure.repository.school;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;
import pl.edziennik.domain.school.SchoolConfiguration;

@RepositoryDefinition(domainClass = SchoolConfiguration.class, idClass = SchoolConfigurationId.class)
public interface SchoolConfigurationQueryRepository {

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.school.config.SchoolConfigSummaryView(s.schoolId,sc.schoolConfigurationId, sc.averageType) " +
            "FROM School s " +
            "JOIN s.schoolConfiguration sc ")
    SchoolConfigSummaryView getSchoolConfigurationView(SchoolId schoolId);

}
