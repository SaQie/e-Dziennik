package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Weight implements Serializable {

    @JsonValue
    private final Integer value;

    private Weight(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static Weight of(@NotNull Integer value) {
        return new Weight(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
