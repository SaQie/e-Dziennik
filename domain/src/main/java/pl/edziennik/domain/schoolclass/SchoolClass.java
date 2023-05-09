package pl.edziennik.domain.schoolclass;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(SchoolClassId.class)
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_class_id_seq")
    @SequenceGenerator(name = "school_class_id_seq", sequenceName = "school_class_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "class_name"))
    })
    private Name className;

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
