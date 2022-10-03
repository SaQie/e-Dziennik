package pl.edziennik.eDziennik.server.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.schoollevel.SchoolLevel;
import pl.edziennik.eDziennik.server.student.Student;
import pl.edziennik.eDziennik.server.teacher.Teacher;
import pl.edziennik.eDziennik.server.subject.SubjectClassLink;
import pl.edziennik.eDziennik.server.basics.BasicEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class School implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_level_id")
    private SchoolLevel schoolLevel;

    @OneToMany(mappedBy = "school")
    private Collection<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    private Collection<SubjectClassLink> subjectClassLinks = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    private Collection<Teacher> teachers = new ArrayList<>();

    public School(String name, String adress, String postalCode, String city, String nip, String regon, String phoneNumber) {
        this.name = name;
        this.adress = adress;
        this.postalCode = postalCode;
        this.city = city;
        this.nip = nip;
        this.regon = regon;
        this.phoneNumber = phoneNumber;
    }

    public void addSubjectClassLinks(SubjectClassLink subjectClassLink){
        subjectClassLinks.add(subjectClassLink);
        subjectClassLink.setSchool(this);
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
        teacher.setSchool(this);
    }

    public void addStudent(Student student){
        students.add(student);
        student.setSchool(this);
    }

    public void setSchoolLevel(SchoolLevel schoolLevel) {
        this.schoolLevel = schoolLevel;
    }


}
