package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.pl.PESEL;

import java.io.Serializable;


@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Pesel implements ValueObject, Serializable {

    @JsonValue
    @PESEL(message = "{pesel.invalid}")
    private final String value;

    private Pesel(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Pesel of(String value) {
        return new Pesel(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
