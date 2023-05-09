package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Accessors(fluent = true)
public class PhoneNumber {

    @JsonValue
    private final String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
