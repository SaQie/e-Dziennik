package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
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
public class ChatContent implements ValueObject, Serializable {


    @JsonValue
    @NotEmpty(message = "{address.empty}")
    private final String value;

    private ChatContent(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ChatContent of(@NotEmpty String value) {
        return new ChatContent(value);
    }

    @Override
    public String toString() {
        return value;
    }


}
