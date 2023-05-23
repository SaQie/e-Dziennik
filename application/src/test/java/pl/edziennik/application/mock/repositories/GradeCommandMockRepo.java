package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;

import java.util.HashMap;
import java.util.Map;

public class GradeCommandMockRepo implements GradeCommandRepository {

    private final Map<GradeId, Grade> database;

    public GradeCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public Grade save(Grade grade) {
        database.put(grade.getGradeId(), grade);
        return database.get(grade.getGradeId());
    }

    @Override
    public Grade getReferenceById(GradeId gradeId) {
        return database.get(gradeId);
    }

    @Override
    public Grade getByGradeId(GradeId gradeId) {
        return database.get(gradeId);
    }
}
