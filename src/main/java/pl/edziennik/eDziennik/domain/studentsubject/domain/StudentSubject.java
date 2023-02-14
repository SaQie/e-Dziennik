package pl.edziennik.eDziennik.domain.studentsubject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentSubject extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_subject_id_seq")
    @SequenceGenerator(name = "student_subject_id_seq", sequenceName = "student_subject_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "studentSubject")
    private List<Grade> grades = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Subject subject;

    @Override
    public boolean isNew() {
        return (id == null);
    }


    public void addGrade(Grade grade){
        this.grades.add(grade);
        grade.setStudentSubject(this);
    }


}
