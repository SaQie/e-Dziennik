package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.exception.InvalidIdentifierException;
import pl.edziennik.common.valueobject.base.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class GroovyScriptStatusId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static GroovyScriptStatusId create() {
        return new GroovyScriptStatusId(UUID.randomUUID());
    }

    @JsonCreator
    protected GroovyScriptStatusId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected GroovyScriptStatusId(UUID uuid) {
        this.id = uuid;
    }

    public static GroovyScriptStatusId of(UUID uuid) {
        return new GroovyScriptStatusId(uuid);
    }

    public static GroovyScriptStatusId of(String value) {
        return new GroovyScriptStatusId(value);
    }

    public static class PredefinedRow {

        public static final GroovyScriptStatusId EXECUTING = GroovyScriptStatusId.of("c578961a-3947-11ee-be56-0242ac120002");
        public static final GroovyScriptStatusId SUCCESS = GroovyScriptStatusId.of("c5789a84-3947-11ee-be56-0242ac120002");
        public static final GroovyScriptStatusId ERROR = GroovyScriptStatusId.of("c5789bce-3947-11ee-be56-0242ac120002");

    }

}
