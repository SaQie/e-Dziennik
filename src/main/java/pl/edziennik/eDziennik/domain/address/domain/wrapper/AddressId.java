package pl.edziennik.eDziennik.domain.address.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class AddressId implements Serializable, Identifier {


    private final Long id;

    @JsonCreator
    public AddressId(Long id) {
        this.id = id;
    }

    public AddressId() {
        this.id = null;
    }

    public static AddressId wrap(Long value) {
        return new AddressId(value);
    }


}