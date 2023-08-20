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
public class FirstName implements StringValueObject {

    @JsonValue
    @NotEmpty(message = "{firstName.empty}")
    private final String value;

    private FirstName(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FirstName of(@NotEmpty String value) {
        return new FirstName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
