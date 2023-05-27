package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class JournalNumber {

    @JsonValue
    private final Integer value;

    private JournalNumber(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static JournalNumber of(Integer value) {
        return new JournalNumber(value);
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(value).toString();
    }
}
