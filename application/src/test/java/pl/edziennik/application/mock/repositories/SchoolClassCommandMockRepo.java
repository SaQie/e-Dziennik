package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SchoolClassCommandMockRepo implements SchoolClassCommandRepository {

    private final Map<SchoolClassId, SchoolClass> database;

    public SchoolClassCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean isSchoolClassBelongToSchool(SchoolClassId schoolClassId, SchoolId schoolId) {
        SchoolClass schoolClass = database.get(schoolClassId);
        return schoolClass.getSchool().getSchoolId().equals(schoolId);
    }

    @Override
    public Optional<SchoolClass> findById(SchoolClassId schoolClassId) {
        return Optional.ofNullable(database.get(schoolClassId));
    }

    @Override
    public boolean existsByName(Name name, SchoolId schoolId) {
        List<SchoolClass> schoolClasses = database.values().stream()
                .filter(item -> item.getSchool().getSchoolId().equals(schoolId) && item.getClassName().equals(name))
                .toList();
        return !schoolClasses.isEmpty();
    }

    @Override
    public SchoolClass save(SchoolClass schoolClass) {
        this.database.put(schoolClass.getSchoolClassId(), schoolClass);
        return this.database.get(schoolClass.getSchoolClassId());
    }

    @Override
    public SchoolClass getReferenceById(SchoolClassId schoolClassId) {
        return database.get(schoolClassId);
    }

    @Override
    public SchoolClass getBySchoolClassId(SchoolClassId schoolClassId) {
        return database.get(schoolClassId);
    }
}
