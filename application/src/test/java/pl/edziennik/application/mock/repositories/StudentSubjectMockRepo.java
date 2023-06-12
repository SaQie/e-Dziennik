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
        database.put(studentSubject.getStudentSubjectId(), studentSubject);
        return database.get(studentSubject.getStudentSubjectId());
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
                .filter(item -> item.getStudent().getStudentId().equals(studentId))
                .filter(item -> item.getSubject().getSubjectId().equals(subjectId))
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public StudentSubject getByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId) {
        List<StudentSubject> list = database.values().stream()
                .filter(item -> item.getStudent().getStudentId().equals(studentId))
                .filter(item -> item.getSubject().getSubjectId().equals(subjectId))
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
