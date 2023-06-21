package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Nip implements ValueObject, Serializable {

    @JsonValue
    @org.hibernate.validator.constraints.pl.NIP(message = "{nip.invalid}")
    private final String value;

    private Nip(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Nip of(String value) {
        return new Nip(value);
    }

    @Override
    public String toString() {
        return value;
    }
}