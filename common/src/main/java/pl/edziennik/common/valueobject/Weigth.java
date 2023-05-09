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
public class Weigth {
    @JsonValue
    private final Integer value;

    private Weigth(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static Weigth of(Integer value) {
        return new Weigth(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
