package pl.edziennik.eDziennik.server.schoolclass.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class SchoolClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_class_id_seq")
    @SequenceGenerator(name = "school_class_id_seq", sequenceName = "school_class_id_seq", allocationSize = 1)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass", orphanRemoval = true)
    private Collection<Student> students = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    public SchoolClass(String className) {
        this.className = className;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public void setSchool(School school){
        this.school = school;
    }


    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }
}
