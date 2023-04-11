package pl.edziennik.eDziennik.domain.schoolclass.domain;

import lombok.*;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_class_id_seq")
    @SequenceGenerator(name = "school_class_id_seq", sequenceName = "school_class_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private final List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "schoolClass")
    private final List<Subject> subjects = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    private LocalDate createdDate;


    public SchoolClassId getSchoolClassId() {
        return SchoolClassId.wrap(id);
    }

    public SchoolClass(String className) {
        this.className = className;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.setSchoolClass(this);
    }
}
