package pl.edziennik.domain.schoolclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class SchoolClassId implements Serializable, Identifier {

    @JsonProperty(value = "schoolClassId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    public SchoolClassId(Long id) {
        this.id = id;
    }

    public SchoolClassId() {
        this.id = null;
    }

    public static SchoolClassId wrap(Long value) {
        return new SchoolClassId(value);
    }


}