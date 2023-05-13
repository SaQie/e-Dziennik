package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Name {

    @JsonValue
    @NotEmpty(message = "{name.empty}")
    private final String value;

    private Name(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Name of(String value) {
        return new Name(value);
    }

    @Override
    public String toString() {
        return value;
    }
}