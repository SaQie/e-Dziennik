package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.vo.Nip;
import pl.edziennik.common.valueobject.vo.PhoneNumber;
import pl.edziennik.common.valueobject.vo.Regon;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class School {

    @EmbeddedId
    private SchoolId schoolId;

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

    @OneToOne(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Director director;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SchoolLevel schoolLevel;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private SchoolConfiguration schoolConfiguration;

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<>();

    @Version
    private Long version;

    @Builder
    public static School of(SchoolId schoolId, Name name, Nip nip, Regon regon, PhoneNumber phoneNumber, Address address,
                            SchoolLevel schoolLevel, SchoolConfigurationProperties properties) {
        School school = new School();

        school.name = name;
        school.regon = regon;
        school.address = address;
        school.phoneNumber = phoneNumber;
        school.schoolLevel = schoolLevel;
        school.nip = nip;
        school.schoolId = schoolId;
        school.schoolConfiguration = SchoolConfiguration.createConfigFromProperties(properties);

        return school;
    }

    public void assignDirector(Director director) {
        if (this.director == null) {
            this.director = director;
        }
    }

}
