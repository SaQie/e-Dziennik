package pl.edziennik.common.valueobject;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Email implements ValueObject{

    @JsonValue
    @jakarta.validation.constraints.Email(message = "{email.is.not.valid}")
    @NotEmpty(message = "{email.empty}")
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Email of(String value) {
        return new Email(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
