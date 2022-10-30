package pl.edziennik.eDziennik.server.studensubject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentSubject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_subject_class_id_seq")
    @SequenceGenerator(name = "student_subject_id_seq", sequenceName = "student_subject_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "studentSubject", orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;


    public void addRating(Grade grade){
        this.grades.add(grade);
        grade.setStudentSubject(this);
    }


}
