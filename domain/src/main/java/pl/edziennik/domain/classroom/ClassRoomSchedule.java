package pl.edziennik.domain.classroom;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.lessonplan.LessonPlan;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassRoomSchedule {

    @EmbeddedId
    private ClassRoomScheduleId classRoomScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.DETACH,CascadeType.REFRESH})
    private LessonPlan lessonPlan;

    @Embedded
    @Column(nullable = false)
    private TimeFrame timeFrame;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    })
    private Description description;

    @Version
    private Long version;

    @Builder
    public static ClassRoomSchedule of(ClassRoomScheduleId classRoomScheduleId, ClassRoom classRoom,
                                       TimeFrame timeFrame, Description description, LessonPlan lessonPlan) {
        ClassRoomSchedule classRoomSchedule = new ClassRoomSchedule();

        classRoomSchedule.classRoom = classRoom;
        classRoomSchedule.timeFrame = timeFrame;
        classRoomSchedule.description = description;
        classRoomSchedule.classRoomScheduleId = classRoomScheduleId;
        classRoomSchedule.lessonPlan = lessonPlan;

        return classRoomSchedule;
    }


}
