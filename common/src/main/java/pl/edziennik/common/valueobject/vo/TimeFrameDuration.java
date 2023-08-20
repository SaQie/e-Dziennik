package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.base.LongValueObject;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class TimeFrameDuration implements LongValueObject {

    @JsonValue
    private final Long value;

    private TimeFrameDuration(Long value) {
        this.value = value;
    }

    @JsonCreator
    public static TimeFrameDuration of(@NotEmpty Long value) {
        return new TimeFrameDuration(value);
    }



}
