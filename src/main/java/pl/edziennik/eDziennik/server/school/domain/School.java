package pl.edziennik.eDziennik.server.school.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class School implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_id_seq")
    @SequenceGenerator(name = "school_id_seq", sequenceName = "school_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolLevel schoolLevel;

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private Collection<SchoolClass> schoolClasses = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private Collection<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
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
