package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.exception.InvalidIdentifierException;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class SchoolLevelId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static SchoolLevelId create() {
        return new SchoolLevelId(UUID.randomUUID());
    }

    @JsonCreator
    protected SchoolLevelId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected SchoolLevelId(UUID uuid) {
        this.id = uuid;
    }


    public static SchoolLevelId of(UUID uuid) {
        return new SchoolLevelId(uuid);
    }

    public static SchoolLevelId of(String value) {
        return new SchoolLevelId(value);
    }

    public static class PredefinedRow {

        public static final SchoolLevelId PRIMARY_SCHOOL = SchoolLevelId.of("ff131a86-1f42-11ee-be56-0242ac120002");
        public static final SchoolLevelId HIGH_SCHOOL = SchoolLevelId.of("ff131d60-1f42-11ee-be56-0242ac120002");
        public static final SchoolLevelId UNIVERSITY = SchoolLevelId.of("ff1330f2-1f42-11ee-be56-0242ac120002");

    }

}