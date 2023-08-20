package pl.edziennik.domain.attendance;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.AbsenceJustificationId;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AbsenceJustification {

    @EmbeddedId
    private AbsenceJustificationId absenceJustificationId = AbsenceJustificationId.create();


    @Version
    private Long version;
}
