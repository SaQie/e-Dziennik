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
public class AdminId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static AdminId create() {
        return new AdminId(UUID.randomUUID());
    }


    @JsonCreator
    protected AdminId(String value) {
        this.id = UUID.fromString(value);
    }

    protected AdminId(UUID uuid) {
        this.id = uuid;
    }

    public static AdminId of(UUID uuid) {
        return new AdminId(uuid);
    }

    public static AdminId of(String value){
        return new AdminId(value);
    }

}