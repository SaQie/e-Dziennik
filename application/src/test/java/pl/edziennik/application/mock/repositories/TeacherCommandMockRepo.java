package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeacherCommandMockRepo implements TeacherCommandRepository {

    private final Map<TeacherId, Teacher> database;

    public TeacherCommandMockRepo() {
        this.database = new HashMap<>();
    }



    @Override
    public Teacher save(Teacher teacher) {
        this.database.put(teacher.teacherId(), teacher);
        return this.database.get(teacher.teacherId());
    }

    @Override
    public Optional<Teacher> findById(TeacherId teacherId) {
        return Optional.ofNullable(database.get(teacherId));
    }

    @Override
    public Teacher getReferenceById(TeacherId teacherId) {
        return database.get(teacherId);
    }

    @Override
    public List<Teacher> getTeachersByUserIds(List<UserId> userIds) {
        return database.values().stream()
                .filter(item -> userIds.contains(item.user().userId()))
                .toList();
    }

    @Override
    public void deleteAll(Iterable<Teacher> teacherIds) {
        database.clear();
    }

    @Override
    public boolean isAlreadySupervisingTeacher(TeacherId teacherId) {
        Teacher teacher = database.get(teacherId);
        if (teacher == null) {
            return false;
        }
        List<SchoolClass> schoolClasses = teacher.school().schoolClasses().stream()
                .filter(item -> item.teacher().teacherId().equals(teacherId))
                .toList();
        return !schoolClasses.isEmpty();
    }

    @Override
    public boolean isAssignedToSchool(TeacherId teacherId, SchoolId schoolId) {
        Teacher teacher = database.get(teacherId);
        return teacher.school().schoolId().equals(schoolId);
    }

    @Override
    public Teacher getByTeacherId(TeacherId teacherId) {
        return database.get(teacherId);
    }

    @Override
    public Teacher getByTeacherIdWithSchoolConfig(TeacherId teacherId) {
        return database.get(teacherId);
    }

    @Override
    public boolean isTeacherAccountNotActive(TeacherId teacherId) {
        Teacher teacher = database.get(teacherId);
        if (teacher == null) {
            return true;
        }
        return !teacher.user().isActive();
    }
}
