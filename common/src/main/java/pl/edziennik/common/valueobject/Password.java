package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Password {

    @JsonValue
    @NotEmpty(message = "{password.empty}")
    @Size(min = 5, message = "{password.size}")
    private final String value;

    private Password(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Password of(String value) {
        return new Password(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
