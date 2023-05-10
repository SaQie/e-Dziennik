package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class StudentSubjectId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    @JsonCreator
    protected StudentSubjectId(String value) {
        this.id = UUID.fromString(value);
    }

    protected StudentSubjectId(UUID uuid) {
        this.id = uuid;
    }

    public static StudentSubjectId create() {
        return new StudentSubjectId(UUID.randomUUID());
    }

    public static StudentSubjectId of(UUID uuid) {
        return new StudentSubjectId(uuid);
    }

    public static StudentSubjectId of(String value) {
        return new StudentSubjectId(value);
    }

}