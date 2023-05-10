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
public class SettingsId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static SettingsId create() {
        return new SettingsId(UUID.randomUUID());
    }

    @JsonCreator
    protected SettingsId(String value) {
        this.id = UUID.fromString(value);
    }

    protected SettingsId(UUID uuid) {
        this.id = uuid;
    }


    public static SettingsId of(UUID uuid) {
        return new SettingsId(uuid);
    }

    public static SettingsId of(String value) {
        return new SettingsId(value);
    }

}