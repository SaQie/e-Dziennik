package pl.edziennik.eDziennik.domain.user.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basics.vo.ValueObject;

import java.io.Serializable;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class UserId implements Serializable, ValueObject {


    private final Long id;


    @JsonCreator
    public UserId(Long id) {
        this.id = id;
    }

    public UserId() {
        this.id = null;
    }

    public static UserId wrap(Long value) {
        return new UserId(value);
    }


}