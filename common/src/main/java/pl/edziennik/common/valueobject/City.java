package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class City implements ValueObject{

    @JsonValue
    @NotEmpty(message = "{city.empty}")
    private final String value;

    private City(String value) {
        this.value = value;
    }

    @JsonCreator
    public static City of(String value) {
        return new City(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
