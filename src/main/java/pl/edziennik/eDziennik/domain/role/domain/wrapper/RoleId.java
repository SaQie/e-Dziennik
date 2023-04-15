package pl.edziennik.eDziennik.domain.role.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basics.vo.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class RoleId implements Serializable, Identifier {

    @JsonProperty(value = "roleId", access = JsonProperty.Access.READ_ONLY)
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