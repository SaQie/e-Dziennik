package pl.edziennik.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.TimeFrame;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherSchedule {

    @EmbeddedId
    private TeacherScheduleId teacherScheduleId = TeacherScheduleId.create();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Teacher teacher;

    @Embedded
    private TimeFrame timeFrame;

    @Version
    private Long version;

    @Builder
    public static TeacherSchedule of(Teacher teacher, TimeFrame timeFrame) {
        TeacherSchedule teacherSchedule = new TeacherSchedule();

        teacherSchedule.teacher = teacher;
        teacherSchedule.timeFrame = timeFrame;

        return teacherSchedule;
    }
}
