package pl.edziennik.eDziennik.domain.user.domain.wrapper;

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
@Accessors(fluent = true)
@EqualsAndHashCode
public class UserId implements Serializable, Identifier {


    @JsonProperty(value = "userId", access = JsonProperty.Access.READ_ONLY)
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