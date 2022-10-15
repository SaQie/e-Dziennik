package pl.edziennik.eDziennik.server.schoolclass.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private Collection<Student> students = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervising_teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "schoolClass")
    private Collection<SubjectClassLink> subjectClassLinks = new ArrayList<>();

    public SchoolClass(String className) {
        this.className = className;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public void addSubjectClassLinks(SubjectClassLink subjectClassLink) {
        subjectClassLinks.add(subjectClassLink);
        subjectClassLink.setSchoolClass(this);
    }

    public void setSchool(School school){
        this.school = school;
    }


    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }
}
