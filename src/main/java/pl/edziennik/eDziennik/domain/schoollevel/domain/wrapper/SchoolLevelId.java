package pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper;

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
public class SchoolLevelId implements Serializable, ValueObject {


    private final Long id;


    @JsonCreator
    public SchoolLevelId(Long id) {
        this.id = id;
    }

    public SchoolLevelId() {
        this.id = null;
    }



    public static SchoolLevelId wrap(Long value) {
        return new SchoolLevelId(value);
    }


}