package pl.edziennik.domain.lessonplan;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.LessonOrder;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonPlan {

    @EmbeddedId
    private LessonPlanId lessonPlanId = LessonPlanId.create();

    @Embedded
    private TimeFrame timeFrame;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "lesson_order", nullable = false))
    })
    private LessonOrder lessonOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id", nullable = false)
    private ClassRoom classRoom;

    @Version
    private Long version;

    @Builder
    public static LessonPlan of(SchoolClass schoolClass, Subject subject, ClassRoom classRoom,
                                TimeFrame timeFrame, LessonOrder lessonOrder) {
        LessonPlan lessonPlan = new LessonPlan();

        lessonPlan.classRoom = classRoom;
        lessonPlan.lessonOrder = lessonOrder;
        lessonPlan.schoolClass = schoolClass;
        lessonPlan.subject = subject;
        lessonPlan.timeFrame = timeFrame;

        return new LessonPlan();
    }

}
