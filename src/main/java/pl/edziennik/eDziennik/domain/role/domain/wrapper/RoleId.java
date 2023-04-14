package pl.edziennik.eDziennik.domain.role.domain.wrapper;

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
public class RoleId implements Serializable, ValueObject {


    private final Long id;

    @JsonCreator
    public RoleId(Long id) {
        this.id = id;
    }

    public RoleId() {
        this.id = null;
    }

    public static RoleId wrap(Long value) {
        return new RoleId(value);
    }


}