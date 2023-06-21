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
public class Regon implements ValueObject, Serializable {

    @JsonValue
    @org.hibernate.validator.constraints.pl.REGON(message = "{regon.invalid}")
    private final String value;

    private Regon(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Regon of(String value) {
        return new Regon(value);
    }

    @Override
    public String toString() {
        return value;
    }
}