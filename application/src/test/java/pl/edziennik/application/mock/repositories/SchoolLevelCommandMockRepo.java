package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.infrastructure.repositories.schoollevel.SchoolLevelCommandRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SchoolLevelCommandMockRepo implements SchoolLevelCommandRepository {

    public static final SchoolLevelId UNIVERSITY_SCHOOL_LEVEL_ID = SchoolLevelId.of(UUID.randomUUID());
    public static final SchoolLevelId PRIMARY_SCHOOL_LEVEL_ID = SchoolLevelId.of(UUID.randomUUID());
    public static final SchoolLevelId HIGH_SCHOOL_LEVEL_ID = SchoolLevelId.of(UUID.randomUUID());

    private final HashMap<SchoolLevelId, SchoolLevel> database = new HashMap<>();

    public SchoolLevelCommandMockRepo() {
        this.database.put(UNIVERSITY_SCHOOL_LEVEL_ID, SchoolLevel.of(UNIVERSITY_SCHOOL_LEVEL_ID, Name.of("University")));
        this.database.put(PRIMARY_SCHOOL_LEVEL_ID, SchoolLevel.of(PRIMARY_SCHOOL_LEVEL_ID, Name.of("Primary")));
        this.database.put(HIGH_SCHOOL_LEVEL_ID, SchoolLevel.of(HIGH_SCHOOL_LEVEL_ID, Name.of("High")));
    }

    @Override
    public Optional<SchoolLevel> findById(SchoolLevelId schoolLevelId) {
        return Optional.ofNullable(this.database.get(schoolLevelId));
    }
}
