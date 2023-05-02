package pl.edziennik.eDziennik.domain.grade.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class GradeId implements Serializable, Identifier {

    @JsonProperty(value = "gradeId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonCreator
    public GradeId(Long id) {
        this.id = id;
    }

    public GradeId() {
        this.id = null;
    }

    public static GradeId wrap(Long value) {
        return new GradeId(value);
    }


}