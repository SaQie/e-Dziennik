package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Nip;
import pl.edziennik.common.valueobject.PhoneNumber;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(SchoolId.class)
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_id_seq")
    @SequenceGenerator(name = "school_id_seq", sequenceName = "school_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private Name name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nip"))
    })
    private Nip nip;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "regon"))
    })
    private Regon regon;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "phone_number"))
    })
    private PhoneNumber phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolLevel schoolLevel;

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<>();

    public School(Name name, Nip nip, Regon regon, PhoneNumber phoneNumber, Address address) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.setSchool(this);
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public SchoolId getSchoolId() {
        return SchoolId.wrap(id);
    }

}
