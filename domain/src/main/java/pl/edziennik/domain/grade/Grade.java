package pl.edziennik.domain.grade;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Grade {

    @EmbeddedId
    private GradeId gradeId = GradeId.create();

    @Column(nullable = false)
    private pl.edziennik.common.enums.Grade grade = pl.edziennik.common.enums.Grade.ONE;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "weight", nullable = false))
    })
    private Weight weight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    })
    private Description description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StudentSubject studentSubject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Teacher teacher;

    @Column(nullable = false)
    private LocalDate createdDate;

    @Version
    private Long version;

    @Builder
    public static Grade of(pl.edziennik.common.enums.Grade gradeConst, Weight weight, Description description, StudentSubject studentSubject,
                           Teacher teacher) {
        Grade grade = new Grade();

        grade.createdDate = LocalDate.now();
        grade.teacher = teacher;
        grade.description = description;
        grade.studentSubject = studentSubject;
        grade.grade = gradeConst;
        grade.weight = weight;

        return grade;
    }

    @PreUpdate
    protected void onUpdate() {
        this.createdDate = LocalDate.now();
    }


}
