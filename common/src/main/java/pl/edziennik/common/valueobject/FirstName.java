package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class FirstName {

    @JsonValue
    private final String value;

    private FirstName(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FirstName of(String value) {
        return new FirstName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
