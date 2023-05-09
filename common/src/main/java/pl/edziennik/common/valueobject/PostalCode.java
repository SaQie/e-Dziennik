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
public class PostalCode {
    @JsonValue
    private final String value;

    private PostalCode(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PostalCode of(String value) {
        return new PostalCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}