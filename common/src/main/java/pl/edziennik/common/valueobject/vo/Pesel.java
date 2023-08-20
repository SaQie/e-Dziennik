package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.pl.PESEL;
import pl.edziennik.common.valueobject.base.StringValueObject;


@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Pesel implements StringValueObject {

    @JsonValue
    @PESEL(message = "{pesel.invalid}")
    private final String value;

    private Pesel(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Pesel of(@NotEmpty String value) {
        return new Pesel(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
