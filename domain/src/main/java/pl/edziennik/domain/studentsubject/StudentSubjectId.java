package pl.edziennik.domain.studentsubject;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class StudentSubjectId implements Serializable, Identifier {

    @JsonProperty(value = "studentSubjectId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;


    public StudentSubjectId(Long id) {
        this.id = id;
    }

    public StudentSubjectId() {
        this.id = null;
    }

    public static StudentSubjectId wrap(Long value) {
        return new StudentSubjectId(value);
    }


}