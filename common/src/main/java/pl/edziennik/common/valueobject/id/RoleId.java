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
public class RoleId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static RoleId create() {
        return new RoleId(UUID.randomUUID());
    }


    @JsonCreator
    protected RoleId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected RoleId(UUID uuid) {
        this.id = uuid;
    }

    public static RoleId of(UUID uuid) {
        return new RoleId(uuid);
    }

    public static RoleId of(String value){
        return new RoleId(value);
    }

}