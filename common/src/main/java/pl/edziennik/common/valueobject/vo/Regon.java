package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.base.StringValueObject;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Regon implements StringValueObject {

    @JsonValue
    @org.hibernate.validator.constraints.pl.REGON(message = "{regon.invalid}")
    private final String value;

    private Regon(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Regon of(@NotEmpty String value) {
        return new Regon(value);
    }

    @Override
    public String toString() {
        return value;
    }
}