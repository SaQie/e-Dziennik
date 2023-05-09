package pl.edziennik.domain.grade;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Weigth;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(GradeId.class)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_id_seq")
    @SequenceGenerator(name = "grade_id_seq", sequenceName = "grade_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

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


    public Grade(GradeConst grade, Weigth weight, Description description) {
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }

    public GradeId getGradeId() {
        return GradeId.wrap(id);
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
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
