package pl.edziennik.domain.classroom;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.vo.TimeFrame;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassRoomSchedule {

    @EmbeddedId
    private ClassRoomScheduleId classRoomScheduleId = ClassRoomScheduleId.create();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ClassRoom classRoom;

    @Embedded
    private TimeFrame timeFrame;

    @Version
    private Long version;

    @Builder
    public static ClassRoomSchedule of(ClassRoom classRoom, TimeFrame timeFrame) {
        ClassRoomSchedule classRoomSchedule = new ClassRoomSchedule();

        classRoomSchedule.classRoom = classRoom;
        classRoomSchedule.timeFrame = timeFrame;

        return classRoomSchedule;
    }


}
