package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentSubjectMockRepo implements StudentSubjectCommandRepository {

    private final Map<StudentSubjectId, StudentSubject> database;

    public StudentSubjectMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean isStudentAlreadyAssignedToSubject(StudentId studentId, SubjectId subjectId) {
        return getReferenceByStudentStudentIdAndSubjectSubjectId(studentId, subjectId) != null;
    }

    @Override
    public StudentSubject save(StudentSubject studentSubject) {
        database.put(studentSubject.studentSubjectId(), studentSubject);
        return database.get(studentSubject.studentSubjectId());
    }

    @Override
    public Optional<StudentSubject> findById(StudentSubjectId studentSubjectId) {
        return Optional.ofNullable(database.get(studentSubjectId));
    }

    @Override
    public List<Grade> getStudentSubjectGrades(StudentSubjectId studentSubjectId) {
        return null;
    }

    @Override
    public StudentSubject getReferenceByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId) {
        List<StudentSubject> list = database.values().stream()
                .filter(item -> item.student().studentId().equals(studentId))
                .filter(item -> item.subject().subjectId().equals(subjectId))
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public StudentSubject getByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId) {
        List<StudentSubject> list = database.values().stream()
                .filter(item -> item.student().studentId().equals(studentId))
                .filter(item -> item.subject().subjectId().equals(subjectId))
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void deleteByStudentIdAndSubjectId(StudentId studentId, SubjectId subjectId) {
        List<StudentSubject> studentSubjects = database.values().stream()
                .filter(studentSubject -> studentSubject.student().studentId().equals(studentId))
                .filter(studentSubject -> studentSubject.subject().subjectId().equals(subjectId))
                .toList();

        if (studentSubjects.isEmpty()) {
            return;
        }

        database.remove(studentSubjects.get(0).studentSubjectId());
    }

    @Override
    public boolean existsGradesAssignedToStudentSubject(StudentId studentId, SubjectId subjectId) {
        return database.values()
                .stream()
                .filter(studentSubject -> studentSubject.student().studentId().equals(studentId))
                .filter(studentSubject -> studentSubject.subject().subjectId().equals(subjectId))
                .anyMatch(studentSubject -> !studentSubject.grades().isEmpty());
    }
}
