package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class PostalCode implements ValueObject, Serializable {

    @JsonValue
    @NotEmpty(message = "{postalCode.empty}")
    @Size(min = 6, max = 6, message = "{postalCode.size}")
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