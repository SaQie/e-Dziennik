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
public class Description {

    @JsonValue
    private final String value;

    private Description(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Description of(String value) {
        return new Description(value);
    }

    @Override
    public String toString() {
        return value;
    }
}