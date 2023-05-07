package pl.edziennik.domain.student;

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
public class StudentId implements Serializable, Identifier {

    @JsonProperty(value = "studentId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;


    @JsonCreator
    public StudentId(Long id) {
        this.id = id;
    }

    public StudentId() {
        this.id = null;
    }

    public static StudentId wrap(Long value) {
        return new StudentId(value);
    }


}
