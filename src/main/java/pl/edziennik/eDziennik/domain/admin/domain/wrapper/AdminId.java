package pl.edziennik.eDziennik.domain.admin.domain.wrapper;

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
public class AdminId implements Serializable, Identifier {


    @JsonProperty(value = "adminId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonCreator
    public AdminId(Long id) {
        this.id = id;
    }

    public AdminId() {
        this.id = null;
    }

    public static AdminId wrap(Long value) {
        return new AdminId(value);
    }


}