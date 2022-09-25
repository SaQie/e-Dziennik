package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class School {

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
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "school")
    private Set<SubjectClassLink> subjectClassLinks = new HashSet<>();

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers = new HashSet<>();

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
