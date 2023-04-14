package pl.edziennik.eDziennik.domain.admin.domain.wrapper;

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
public class AdminId implements Serializable, ValueObject {


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