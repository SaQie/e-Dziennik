package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.domain.school.SchoolConfiguration;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationCommandRepository;

import java.util.HashMap;
import java.util.Map;

public class SchoolConfigurationCommandMockRepo implements SchoolConfigurationCommandRepository {

    private final Map<SchoolConfigurationId, SchoolConfiguration> database;

    public SchoolConfigurationCommandMockRepo() {
        this.database = new HashMap<>();
    }


    @Override
    public SchoolConfiguration getSchoolConfigurationBySchoolConfigurationId(SchoolConfigurationId schoolConfigurationId) {
        return database.get(schoolConfigurationId);
    }

    @Override
    public SchoolConfiguration save(SchoolConfiguration schoolConfiguration) {
        database.put(schoolConfiguration.getSchoolConfigurationId(), schoolConfiguration);
        return database.get(schoolConfiguration.getSchoolConfigurationId());
    }
}
