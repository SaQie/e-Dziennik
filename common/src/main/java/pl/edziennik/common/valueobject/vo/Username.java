package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class Username implements StringValueObject {
    @JsonValue
    @NotEmpty(message = "{username.empty}")
    @Size(min = 4, message = "{username.size}")
    private final String value;

    private Username(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Username of(@NotEmpty String value) {
        return new Username(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
