package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.base.IntegerValueObject;

import java.util.Objects;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class TimeFrameDuration implements IntegerValueObject {

    @JsonValue
    private final Integer value;

    private TimeFrameDuration(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static TimeFrameDuration of(@NotEmpty Integer value) {
        return new TimeFrameDuration(value);
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(value).toString();
    }

    public boolean isGreaterThan(TimeFrameDuration timeFrameDuration) {
        return timeFrameDuration.value < this.value;
    }
}
