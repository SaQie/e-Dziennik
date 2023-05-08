package pl.edziennik.domain.school;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class SchoolId implements Serializable, Identifier {

    @JsonProperty(value = "schoolId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonCreator
    public SchoolId(Long id) {
        this.id = id;
    }

    public SchoolId() {
        this.id = null;
    }

    public static SchoolId wrap(Long value) {
        return new SchoolId(value);
    }


}