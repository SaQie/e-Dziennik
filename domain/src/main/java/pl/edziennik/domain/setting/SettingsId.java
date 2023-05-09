package pl.edziennik.domain.setting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class SettingsId implements Serializable, Identifier {

    @JsonProperty(value = "settingId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonCreator
    public SettingsId(Long id) {
        this.id = id;
    }

    public SettingsId() {
        this.id = null;
    }

    public static SettingsId wrap(Long value) {
        return new SettingsId(value);
    }


}