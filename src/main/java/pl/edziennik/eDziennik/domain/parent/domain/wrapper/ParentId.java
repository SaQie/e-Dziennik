package pl.edziennik.eDziennik.domain.parent.domain.wrapper;

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
public class ParentId implements Serializable, ValueObject {


    private final Long id;

    @JsonCreator
    public ParentId(Long id) {
        this.id = id;
    }

    public ParentId() {
        this.id = null;
    }

    public static ParentId wrap(Long value) {
        return new ParentId(value);
    }


}