package pl.edziennik.common.valueobject.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.exception.InvalidParameterException;
import pl.edziennik.common.valueobject.base.UUIDValueObject;

import java.util.Objects;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class Token implements UUIDValueObject {

    private final UUID value;

    private Token(UUID value) {
        this.value = value;
    }

    public static Token of(@NotEmpty String token) {
        try {
            UUID uuid = UUID.fromString(token);
            return new Token(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Invalid token value");
        }
    }

    public static Token of(@NotNull UUID uuid) {
        return new Token(uuid);
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(value).toString();
    }

}
