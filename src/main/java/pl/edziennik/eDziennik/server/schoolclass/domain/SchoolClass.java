package pl.edziennik.eDziennik.server.schoolclass.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractEntity;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class SchoolClass extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_class_id_seq")
    @SequenceGenerator(name = "school_class_id_seq", sequenceName = "school_class_id_seq", allocationSize = 1)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "schoolClass")
    private List<Subject> subjects = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    private LocalDate createdDate;

    @Override
    public boolean isNew() {
        return (id == null);
    }

    public SchoolClass(String className) {
        this.className = className;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public void setSchool(School school){
        this.school = school;
    }

    public void setCreatedDate(LocalDate date){
        this.createdDate = date;
    }

    public void setClassName(String className){
        this.className = className;
    }


    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDate.now();
    }
    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
        subject.setSchoolClass(this);
    }
}
