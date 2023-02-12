package pl.edziennik.eDziennik.domain.grade.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;
import pl.edziennik.eDziennik.domain.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Grade extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_id_seq")
    @SequenceGenerator(name = "grade_id_seq", sequenceName = "grade_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated
    private GradeConst grade;
    private int weight;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudentSubject studentSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    private LocalDateTime createdDate;

    @Override
    public boolean isNew() {
        return (id == null);
    }


    public Grade(GradeConst grade, int weight, String description) {
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.createdDate = LocalDateTime.now();
    }


    public enum GradeConst {

        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);

        public int grade;

        GradeConst(int grade) {
            this.grade = grade;
        }

        public static GradeConst getByRating(int grade){
            for (GradeConst ratingConst : GradeConst.values()){
                if (ratingConst.grade == grade){
                    return ratingConst;
                }
            }
            throw new EntityNotFoundException("Grade " + grade + " not found");
        }
    }


}
