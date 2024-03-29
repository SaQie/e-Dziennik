package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.base.StringValueObject;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "value")
public class PhoneNumber implements StringValueObject {

    @JsonValue
    @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
    private final String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PhoneNumber of(@NotEmpty String value) {
        return new PhoneNumber(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
