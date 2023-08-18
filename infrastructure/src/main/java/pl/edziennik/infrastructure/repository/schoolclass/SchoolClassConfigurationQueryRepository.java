package pl.edziennik.infrastructure.repository.schoolclass;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolClassConfigurationId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;

@RepositoryDefinition(domainClass = SchoolClassConfiguration.class, idClass = SchoolClassConfigurationId.class)
public interface SchoolClassConfigurationQueryRepository {

    @Query("SELECT new " +
            " pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView(sc.schoolClassId, sc.className, scc.maxStudentsSize, scc.autoAssignSubjects) " +
            "FROM SchoolClass sc " +
            "JOIN sc.schoolClassConfiguration scc " +
            "WHERE sc.schoolClassId = :schoolClassId ")
    SchoolClassConfigSummaryView getSchoolClassConfigurationSummaryView(SchoolClassId schoolClassId);

}
