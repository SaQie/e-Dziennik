package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;

import java.util.*;

public class SchoolClassCommandMockRepo implements SchoolClassCommandRepository {

    private final Map<SchoolClassId, SchoolClass> database;

    public SchoolClassCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean isSchoolClassBelongToSchool(SchoolClassId schoolClassId, SchoolId schoolId) {
        SchoolClass schoolClass = database.get(schoolClassId);
        return schoolClass.school().schoolId().equals(schoolId);
    }

    @Override
    public Optional<SchoolClass> findById(SchoolClassId schoolClassId) {
        return Optional.ofNullable(database.get(schoolClassId));
    }

    @Override
    public boolean existsByName(Name name, SchoolId schoolId) {
        List<SchoolClass> schoolClasses = database.values().stream()
                .filter(item -> item.school().schoolId().equals(schoolId) && item.className().equals(name))
                .toList();
        return !schoolClasses.isEmpty();
    }

    @Override
    public SchoolClass save(SchoolClass schoolClass) {
        this.database.put(schoolClass.schoolClassId(), schoolClass);
        return this.database.get(schoolClass.schoolClassId());
    }

    @Override
    public SchoolClass getReferenceById(SchoolClassId schoolClassId) {
        return database.get(schoolClassId);
    }

    @Override
    public SchoolClass getBySchoolClassId(SchoolClassId schoolClassId) {
        return database.get(schoolClassId);
    }

    @Override
    public SchoolClass getBySchoolClassIdWithSubjects(SchoolClassId schoolClassId) {
        return database.get(schoolClassId);
    }

    @Override
    public boolean isStudentLimitReached(SchoolClassId schoolClassId) {
        SchoolClass schoolClass = database.get(schoolClassId);
        int studentsCount = schoolClass.students().size();
        Integer maxStudentsSize = schoolClass.schoolClassConfiguration().maxStudentsSize();

        return studentsCount == maxStudentsSize;
    }

    @Override
    public boolean isStudentsExists(SchoolClassId schoolClassId) {
        return database.values()
                .stream()
                .anyMatch(x -> !x.students().isEmpty());
    }

    @Override
    public boolean isSubjectsExists(SchoolClassId schoolClassId) {
        return database.values()
                .stream()
                .anyMatch(schoolClass -> !schoolClass.students().isEmpty());
    }

    @Override
    public void deleteById(SchoolClassId schoolClassId) {
        database.remove(schoolClassId);
    }

    @Override
    public List<LessonPlanId> getLessonPlansIdsBySchoolClassId(SchoolClassId schoolClassId) {
        return Collections.emptyList();
    }
}
