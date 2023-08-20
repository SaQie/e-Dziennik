package pl.edziennik.domain.attendance;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.AttendanceTypeId;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceType {

    @EmbeddedId
    private AttendanceTypeId attendanceTypeId = AttendanceTypeId.create();


    @Version
    private Long version;

}
