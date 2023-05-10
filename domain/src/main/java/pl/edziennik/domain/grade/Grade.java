package pl.edziennik.domain.grade;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Weigth;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Grade {

    @EmbeddedId
    private GradeId gradeId = GradeId.create();

    @Enumerated
    private GradeConst grade;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "weigth"))
    })
    private Weigth weight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description"))
    })
    private Description description;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudentSubject studentSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    private LocalDate createdDate;

    public static Grade of(GradeConst gradeConst, Weigth weight, Description description, StudentSubject studentSubject,
                           Teacher teacher){
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


    public enum GradeConst {

        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);

        public final int grade;

        GradeConst(int grade) {
            this.grade = grade;
        }

        public static GradeConst getByRating(int grade) {
            for (GradeConst ratingConst : GradeConst.values()) {
                if (ratingConst.grade == grade) {
                    return ratingConst;
                }
            }
            throw new EntityNotFoundException("Grade " + grade + " not found");
        }
    }


}
