package pl.edziennik.domain.studentsubject;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class StudentSubject {

    @EmbeddedId
    private StudentSubjectId studentSubjectId = StudentSubjectId.create();

    @OneToMany(mappedBy = "studentSubject")
    private List<Grade> grades = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    private BigDecimal average;

    @Version
    private Long version;

    @Builder
    public static StudentSubject of(Student student, Subject subject) {
        StudentSubject studentSubject = new StudentSubject();

        studentSubject.student = student;
        studentSubject.subject = subject;

        return studentSubject;
    }

    public void changeAverage(BigDecimal average) {
        this.average = average;
    }

}
