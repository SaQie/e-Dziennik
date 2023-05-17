package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentCommandMockRepo implements StudentCommandRepository {

    private final Map<StudentId, Student> database;

    public StudentCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean hasAlreadyAssignedParent(StudentId studentId) {
        Student student = database.get(studentId);
        if (student == null) {
            return false;
        }
        return student.getParent() != null;
    }

    @Override
    public boolean isStudentExistsByPesel(Pesel pesel, Name roleName) {
        List<Student> students = database.values().stream()
                .filter(item -> item.getPersonInformation().pesel().equals(pesel)
                        && item.getUser().getRole().getName().equals(roleName))
                .toList();
        return !students.isEmpty();
    }

    @Override
    public void delete(Student student) {
        database.remove(student.getStudentId());
    }

    @Override
    public Optional<Student> findById(StudentId studentId) {
        return Optional.ofNullable(database.get(studentId));
    }

    @Override
    public Student save(Student student) {
        database.put(student.getStudentId(), student);
        return database.get(student.getStudentId());
    }

    @Override
    public Student getReferenceById(StudentId studentId) {
        return database.get(studentId);
    }

    @Override
    public boolean existsByUsername(Username username) {
        List<Student> students = database.values().stream()
                .filter(item -> item.getUser().getUsername().equals(username))
                .toList();
        return !students.isEmpty();
    }

    @Override
    public boolean existsByEmail(Email email) {
        List<Student> students = database.values().stream()
                .filter(item -> item.getUser().getEmail().equals(email))
                .toList();
        return !students.isEmpty();
    }
}
