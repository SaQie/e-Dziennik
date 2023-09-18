package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectCommandMockRepo implements SubjectCommandRepository {

    private final Map<SubjectId, Subject> database;
    private final SchoolClassCommandRepository schoolClassMockRepo;
    private final StudentCommandRepository studentCommandMockRepo;
    private final GradeCommandRepository gradeCommandRepository;

    public SubjectCommandMockRepo(SchoolClassCommandRepository schoolClassCommandRepository,
                                  StudentCommandRepository studentCommandRepository, GradeCommandRepository gradeCommandRepository) {
        this.database = new HashMap<>();
        this.schoolClassMockRepo = schoolClassCommandRepository;
        this.studentCommandMockRepo = studentCommandRepository;
        this.gradeCommandRepository = gradeCommandRepository;

    }

    @Override
    public boolean isTeacherFromTheSameSchool(SchoolClassId schoolClassId, TeacherId teacherId) {
        SchoolClass schoolClass = schoolClassMockRepo.getReferenceById(schoolClassId);
        if (schoolClass == null) {
            return false;
        }
        return schoolClass.teacher().teacherId().equals(teacherId);
    }

    @Override
    public boolean existsByName(Name name, SchoolClassId schoolClassId) {
        List<Subject> subjects = database.values().stream()
                .filter(item -> item.schoolClass().schoolClassId().equals(schoolClassId))
                .filter(item -> item.name().equals(name))
                .toList();
        return !subjects.isEmpty();
    }

    @Override
    public Subject save(Subject subject) {
        database.put(subject.subjectId(), subject);
        return database.get(subject.subjectId());
    }

    @Override
    public Optional<Subject> findById(SubjectId subjectId) {
        return Optional.ofNullable(database.get(subjectId));
    }

    @Override
    public Subject getBySubjectId(SubjectId subjectId) {
        return database.get(subjectId);
    }

    @Override
    public boolean isStudentFromTheSameSchoolClass(StudentId studentId, SubjectId subjectId) {
        Student student = studentCommandMockRepo.getReferenceById(studentId);
        Subject subject = getReferenceById(subjectId);
        return student.schoolClass().schoolClassId().equals(subject.schoolClass().schoolClassId());
    }

    @Override
    public Subject getReferenceById(SubjectId subjectId) {
        return database.get(subjectId);
    }

    @Override
    public boolean isTeacherFromProvidedSubject(TeacherId teacherId, SubjectId subjectId) {
        Subject subject = getReferenceById(subjectId);
        return subject.teacher().teacherId().equals(teacherId);
    }

    @Override
    public boolean existsGradesAssignedToSubject(SubjectId subjectId) {
        long count = gradeCommandRepository.getGradesBySubjectId(subjectId)
                .stream()
                .filter(grade -> grade.studentSubject().subject().subjectId().equals(subjectId))
                .count();
        return count > 0;
    }

    @Override
    public Name getNameBySubjectId(SubjectId subjectId) {
        return database.get(subjectId).name();
    }

    @Override
    public void deleteBySubjectId(SubjectId subjectId) {
        database.remove(subjectId);
    }
}
