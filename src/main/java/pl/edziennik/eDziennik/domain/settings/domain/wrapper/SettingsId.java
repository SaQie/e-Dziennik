package pl.edziennik.eDziennik.domain.settings.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basics.vo.ValueObject;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class SettingsId implements Serializable, ValueObject {


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