package pl.edziennik.domain.subject;

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
@Accessors(fluent = true)
@EqualsAndHashCode
public class SubjectId implements Serializable, Identifier {

    @JsonProperty(value = "subjectId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;


    @JsonCreator
    public SubjectId(Long id) {
        this.id = id;
    }

    public SubjectId() {
        this.id = null;
    }

    public static SubjectId wrap(Long value) {
        return new SubjectId(value);
    }


}