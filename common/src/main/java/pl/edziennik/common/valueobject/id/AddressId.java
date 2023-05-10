package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AddressId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static AddressId create() {
        return new AddressId(UUID.randomUUID());
    }


    @JsonCreator
    protected AddressId(String value) {
        this.id = UUID.fromString(value);
    }

    protected AddressId(UUID uuid) {
        this.id = uuid;
    }

    public static AddressId of(UUID uuid) {
        return new AddressId(uuid);
    }

    public static AddressId of(String value){
        return new AddressId(value);
    }

}