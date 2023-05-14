package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Weight {
    @JsonValue
    private final Integer value;

    private Weight(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static Weight of(Integer value) {
        return new Weight(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
