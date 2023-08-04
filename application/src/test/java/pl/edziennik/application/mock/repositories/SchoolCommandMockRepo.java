package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Nip;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SchoolCommandMockRepo implements SchoolCommandRepository {

    private final Map<SchoolId, School> database;

    public SchoolCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public School getReferenceById(SchoolId schoolId) {
        return database.get(schoolId);
    }

    @Override
    public boolean isExistsByName(Name name) {
        List<School> schools = database.values().stream()
                .filter(school -> school.name().equals(name))
                .toList();
        return !schools.isEmpty();
    }

    @Override
    public boolean isExistsByNip(Nip nip) {
        List<School> schools = database.values().stream()
                .filter(school -> school.nip().equals(nip))
                .toList();
        return !schools.isEmpty();
    }

    @Override
    public boolean isExistsByRegon(Regon regon) {
        List<School> schools = database.values().stream()
                .filter(school -> school.regon().equals(regon))
                .toList();
        return !schools.isEmpty();
    }

    @Override
    public School save(School school) {
        this.database.put(school.schoolId(), school);
        return this.database.get(school.schoolId());
    }

    @Override
    public Optional<School> findById(SchoolId schoolId) {
        return Optional.ofNullable(this.database.get(schoolId));
    }

    @Override
    public boolean isTeacherExistsInSchool(SchoolId schoolId) {
        School school = this.database.get(schoolId);
        if (school == null) {
            return false;
        }
        return !school.teachers().isEmpty();
    }

    @Override
    public boolean isStudentExistsInSchool(SchoolId schoolId) {
        School school = this.database.get(schoolId);
        if (school == null) {
            return false;
        }
        return !school.students().isEmpty();
    }

    @Override
    public boolean isSchoolClassExistsInSchool(SchoolId schoolId) {
        School school = this.database.get(schoolId);
        if (school == null) {
            return false;
        }
        return !school.schoolClasses().isEmpty();
    }

    @Override
    public boolean isSchoolHasAssignedDirector(SchoolId schoolId) {
        School school = database.get(schoolId);
        if (school == null) return false;
        return school.director() != null;
    }

    @Override
    public void deleteById(SchoolId schoolId) {
        this.database.remove(schoolId);
    }

    @Override
    public School getBySchoolId(SchoolId schoolId) {
        return database.get(schoolId);
    }
}
