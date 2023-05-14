package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repositories.subject.SubjectCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectCommandMockRepo implements SubjectCommandRepository {

    private final Map<SubjectId, Subject> database;
    private final SchoolClassCommandMockRepo schoolClassMockRepo;
    private final StudentCommandMockRepo studentCommandMockRepo;
    private final SubjectCommandMockRepo subjectCommandMockRepo;

    public SubjectCommandMockRepo(SchoolClassCommandMockRepo schoolClassCommandMockRepo,
                                  StudentCommandMockRepo studentCommandMockRepo,
                                  SubjectCommandMockRepo subjectCommandMockRepo) {
        this.database = new HashMap<>();
        this.schoolClassMockRepo = schoolClassCommandMockRepo;
        this.studentCommandMockRepo = studentCommandMockRepo;
        this.subjectCommandMockRepo = subjectCommandMockRepo;
    }

    @Override
    public boolean isTeacherFromTheSameSchool(SchoolClassId schoolClassId, TeacherId teacherId) {
        SchoolClass schoolClass = schoolClassMockRepo.getReferenceById(schoolClassId);
        if (schoolClass == null) {
            return false;
        }
        return schoolClass.getTeacher().getTeacherId().equals(teacherId);
    }

    @Override
    public boolean existsByName(Name name, SchoolClassId schoolClassId) {
        List<Subject> subjects = database.values().stream()
                .filter(item -> item.getSchoolClass().getSchoolClassId().equals(schoolClassId))
                .filter(item -> item.getName().equals(name))
                .toList();
        return !subjects.isEmpty();
    }

    @Override
    public Subject save(Subject subject) {
        database.put(subject.getSubjectId(), subject);
        return database.get(subject.getSubjectId());
    }

    @Override
    public Optional<Subject> findById(SubjectId subjectId) {
        return Optional.ofNullable(database.get(subjectId));
    }

    @Override
    public boolean isStudentFromTheSameSchoolClass(StudentId studentId, SubjectId subjectId) {
        Student student = studentCommandMockRepo.getReferenceById(studentId);
        Subject subject = subjectCommandMockRepo.getReferenceById(subjectId);

        return student.getSchoolClass().getSchoolClassId().equals(subject.getSchoolClass().getSchoolClassId());
    }

    @Override
    public Subject getReferenceById(SubjectId subjectId) {
        return database.get(subjectId);
    }

    @Override
    public boolean isTeacherFromProvidedSubject(TeacherId teacherId, SubjectId subjectId) {
        Subject subject = subjectCommandMockRepo.getReferenceById(subjectId);

        return subject.getTeacher().getTeacherId().equals(teacherId);
    }
}
