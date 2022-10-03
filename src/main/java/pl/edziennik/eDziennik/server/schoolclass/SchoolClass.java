package pl.edziennik.eDziennik.server.schoolclass;

import lombok.*;
import pl.edziennik.eDziennik.server.student.Student;
import pl.edziennik.eDziennik.server.subject.SubjectClassLink;
import pl.edziennik.eDziennik.server.teacher.Teacher;
import pl.edziennik.eDziennik.server.basics.BasicEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class SchoolClass implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private Collection<Student> students = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervising_teacher_id")
    private Teacher teacher;

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


    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }
}
