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
public class SchoolId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static SchoolId create() {
        return new SchoolId(UUID.randomUUID());
    }

    @JsonCreator
    protected SchoolId(String value) {
        this.id = UUID.fromString(value);
    }

    protected SchoolId(UUID uuid) {
        this.id = uuid;
    }


    public static SchoolId of(UUID uuid) {
        return new SchoolId(uuid);
    }

    public static SchoolId of(String value) {
        return new SchoolId(value);
    }

}