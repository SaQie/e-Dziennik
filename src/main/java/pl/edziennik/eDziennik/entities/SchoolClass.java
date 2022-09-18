package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervising_teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "schoolClass")
    private Set<SubjectClassLink> subjectClassLinks = new HashSet<>();

    public void addSubjectClassLinks(SubjectClassLink subjectClassLink) {
        subjectClassLinks.add(subjectClassLink);
        subjectClassLink.setSchoolClass(this);
    }


    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }
}
