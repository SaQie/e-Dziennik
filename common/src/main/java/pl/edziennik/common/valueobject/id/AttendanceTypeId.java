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
public class AttendanceTypeId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    @JsonCreator
    protected AttendanceTypeId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected AttendanceTypeId(UUID uuid) {
        this.id = uuid;
    }

    public static AttendanceTypeId create() {
        return new AttendanceTypeId(UUID.randomUUID());
    }


    public static AttendanceTypeId of(UUID id) {
        return new AttendanceTypeId(id);
    }

    public static AttendanceTypeId of(String value) {
        return new AttendanceTypeId(value);
    }


    public static class PredefinedRow {

        public static final AttendanceTypeId ATTENDANCE = AttendanceTypeId.of("e078f126-3df2-11ee-be56-0242ac120002");
        public static final AttendanceTypeId ABSENCE = AttendanceTypeId.of("e078f4dc-3df2-11ee-be56-0242ac120002");
        public static final AttendanceTypeId EXCUSED_ABSENCE = AttendanceTypeId.of("e078fa4a-3df2-11ee-be56-0242ac120002");
        public static final AttendanceTypeId DELAY = AttendanceTypeId.of("e078fc16-3df2-11ee-be56-0242ac120002");
        public static final AttendanceTypeId OTHER = AttendanceTypeId.of("e078fe50-3df2-11ee-be56-0242ac120002");

    }

}
