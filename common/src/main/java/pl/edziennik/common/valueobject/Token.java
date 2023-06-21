package pl.edziennik.common.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.common.exception.InvalidParameterException;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "value")
public class Token implements Serializable {
    private final UUID value;

    private Token(UUID value) {
        this.value = value;
    }

    public static Token of(String token) {
        try {
            UUID uuid = UUID.fromString(token);
            return new Token(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Invalid token value");
        }
    }

    public static Token of(UUID uuid) {
        return new Token(uuid);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
