package pl.edziennik.eDziennik.domain.grade.domain.wrapper;

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
public class GradeId implements Serializable, ValueObject {


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