package pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper;

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
public class SchoolClassId implements Serializable, ValueObject {


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