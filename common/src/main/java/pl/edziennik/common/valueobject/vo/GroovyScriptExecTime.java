package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.base.LongValueObject;

import java.time.Duration;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class GroovyScriptExecTime implements LongValueObject {

    @JsonValue
    @NotNull
    private final Long value;

    private GroovyScriptExecTime(Long value) {
        this.value = value;
    }

    @JsonCreator
    public static GroovyScriptExecTime of(@NotNull Duration duration) {
        return new GroovyScriptExecTime(duration.toMillis());
    }

    public static GroovyScriptExecTime none() {
        return new GroovyScriptExecTime(0L);
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(value).toString();
    }

}
