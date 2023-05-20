package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;

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
    public boolean isExistsByEmail(Email email) {
        List<Teacher> teachers = database.values().stream()
                .filter(item -> item.getUser().getEmail().equals(email))
                .toList();
        return !teachers.isEmpty();
    }

    @Override
    public boolean isExistsByPesel(Pesel pesel) {
        List<Teacher> teachers = database.values().stream()
                .filter(item -> item.getPersonInformation().pesel().equals(pesel))
                .toList();
        return !teachers.isEmpty();
    }

    @Override
    public boolean isExistsByUsername(Username username) {
        List<Teacher> teachers = database.values().stream()
                .filter(item -> item.getUser().getUsername().equals(username))
                .toList();
        return !teachers.isEmpty();
    }

    @Override
    public Teacher save(Teacher teacher) {
        this.database.put(teacher.getTeacherId(), teacher);
        return this.database.get(teacher.getTeacherId());
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
    public boolean isAlreadySupervisingTeacher(TeacherId teacherId) {
        Teacher teacher = database.get(teacherId);
        if (teacher == null) {
            return false;
        }
        List<SchoolClass> schoolClasses = teacher.getSchool().getSchoolClasses().stream()
                .filter(item -> item.getTeacher().getTeacherId().equals(teacherId))
                .toList();
        return !schoolClasses.isEmpty();
    }

    @Override
    public boolean isAssignedToSchool(TeacherId teacherId, SchoolId schoolId) {
        Teacher teacher = database.get(teacherId);
        return teacher.getSchool().getSchoolId().equals(schoolId);
    }

    @Override
    public Teacher getByTeacherId(TeacherId teacherId) {
        return database.get(teacherId);
    }
}
