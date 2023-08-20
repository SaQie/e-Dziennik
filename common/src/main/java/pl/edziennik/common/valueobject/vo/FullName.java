package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonValue;
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
public class FullName implements StringValueObject {

    @JsonValue
    private final String value;

    private FullName(FirstName firstName, LastName lastName) {
        this.value = firstName + " " + lastName;
    }

    private FullName(String value) {
        this.value = value;
    }

    public static FullName of(FirstName firstName, LastName lastName) {
        return new FullName(firstName, lastName);
    }

    public static FullName of(String value) {
        return new FullName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
