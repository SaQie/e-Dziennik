package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class FullName {

    @JsonValue
    private final String value;

    private FullName(FirstName firstName, LastName lastName) {
        this.value = firstName + " " + lastName;
    }

    private FullName(String value){
        this.value = value;
    }

    public static FullName of(FirstName firstName, LastName lastName) {
        return new FullName(firstName, lastName);
    }

    public static FullName of(String value){
        return new FullName(value);
    }
    @Override
    public String toString() {
        return value;
    }
}