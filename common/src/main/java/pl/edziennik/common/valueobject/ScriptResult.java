package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotEmpty;
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
public class ScriptResult implements ValueObject, Serializable {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @JsonValue
    private final String value;

    private ScriptResult(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ScriptResult of(@NotEmpty String value) {
        return new ScriptResult(value);
    }

    public static ScriptResult of(@NotNull Object obj) {
        return new ScriptResult(obj.toString());
    }

    public ScriptResult(Object obj) {
        this.value = obj.toString();
    }

    @Override
    public String toString() {
        return value;
    }

}
