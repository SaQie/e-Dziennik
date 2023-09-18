package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GradeCommandMockRepo implements GradeCommandRepository {

    private final Map<GradeId, Grade> database;

    public GradeCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public Grade save(Grade grade) {
        database.put(grade.gradeId(), grade);
        return database.get(grade.gradeId());
    }

    @Override
    public Grade getReferenceById(GradeId gradeId) {
        return database.get(gradeId);
    }

    @Override
    public Grade getByGradeId(GradeId gradeId) {
        return database.get(gradeId);
    }

    @Override
    public Optional<Grade> findById(GradeId gradeId) {
        return Optional.ofNullable(database.get(gradeId));
    }

    @Override
    public void deleteById(GradeId gradeId) {
        database.remove(gradeId);
    }

    @Override
    public List<Grade> getGradesBySubjectId(SubjectId subjectId) {
        return database.values()
                .stream()
                .filter(grade -> grade.studentSubject().subject().subjectId().equals(subjectId))
                .toList();
    }
}
