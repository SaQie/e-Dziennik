package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.SchoolClassConfigurationId;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationCommandRepository;

import java.util.HashMap;
import java.util.Map;

public class SchoolClassConfigurationCommandMockRepo implements SchoolClassConfigurationCommandRepository {

    private final Map<SchoolClassConfigurationId, SchoolClassConfiguration> database;

    public SchoolClassConfigurationCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public SchoolClassConfiguration getSchoolClassConfigurationBySchoolClassConfigurationId(SchoolClassConfigurationId schoolClassConfigurationId) {
        return database.get(schoolClassConfigurationId);
    }

    @Override
    public SchoolClassConfiguration save(SchoolClassConfiguration schoolClassConfiguration) {
        database.put(schoolClassConfiguration.schoolClassConfigurationId(), schoolClassConfiguration);
        return database.get(schoolClassConfiguration.schoolClassConfigurationId());
    }
}
