package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Nip;
import pl.edziennik.common.valueobject.PhoneNumber;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class School {

    @EmbeddedId
    private SchoolId schoolId = SchoolId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nip", nullable = false))
    })
    private Nip nip;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "regon", nullable = false))
    })
    private Regon regon;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "phone_number", nullable = false))
    })
    private PhoneNumber phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SchoolLevel schoolLevel;

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<>();

    public static School of(Name name, Nip nip, Regon regon, PhoneNumber phoneNumber, Address address,
                            SchoolLevel schoolLevel) {
        School school = new School();
        school.name = name;
        school.regon = regon;
        school.address = address;
        school.phoneNumber = phoneNumber;
        school.schoolLevel = schoolLevel;
        school.nip = nip;

        return school;
    }

}
