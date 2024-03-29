package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
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
public class Description implements StringValueObject {

    @JsonValue
    private final String value;

    private Description(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Description of(@NotNull String value) {
        return new Description(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public Description append(String value) {
        return new Description(this.value + " " + value);
    }
}
