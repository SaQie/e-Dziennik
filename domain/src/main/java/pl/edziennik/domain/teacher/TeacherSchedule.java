package pl.edziennik.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
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
public class TeacherSchedule {

    @EmbeddedId
    private TeacherScheduleId teacherScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
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
    public static TeacherSchedule of(TeacherScheduleId teacherScheduleId, Teacher teacher, TimeFrame timeFrame, Description description, LessonPlan lessonPlan) {
        TeacherSchedule teacherSchedule = new TeacherSchedule();

        teacherSchedule.teacher = teacher;
        teacherSchedule.timeFrame = timeFrame;
        teacherSchedule.description = description;
        teacherSchedule.teacherScheduleId = teacherScheduleId;
        teacherSchedule.lessonPlan = lessonPlan;

        return teacherSchedule;
    }
}
