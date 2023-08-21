package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
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
public class LessonOrder implements IntegerValueObject {

    @JsonValue
    @NotNull
    private final Integer value;

    private LessonOrder(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static LessonOrder of(@NotNull Integer value) {
        return new LessonOrder(value);
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(value).toString();
    }

    public LessonOrder increase() {
        Integer newValue = value + 1;
        return new LessonOrder(newValue);
    }

}
