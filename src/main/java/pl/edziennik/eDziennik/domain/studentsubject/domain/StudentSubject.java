package pl.edziennik.eDziennik.domain.studentsubject.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectId;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_subject_id_seq")
    @SequenceGenerator(name = "student_subject_id_seq", sequenceName = "student_subject_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @OneToMany(mappedBy = "studentSubject")
    private List<Grade> grades = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;


    public void addGrade(Grade grade) {
        this.grades.add(grade);
        grade.setStudentSubject(this);
    }

    public StudentSubjectId getStudentSubjectId() {
        return StudentSubjectId.wrap(id);
    }


}
