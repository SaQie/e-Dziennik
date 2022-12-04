package pl.edziennik.eDziennik.server.student.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.address.Address;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Student extends BasicUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
    @SequenceGenerator(name = "student_id_seq", sequenceName = "student_id_seq", allocationSize = 1)
    private Long id;

    private String parentFirstName;
    private String parentLastName;
    private String parentPhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Address address;



    public Student(String username, String password, String firstName, String lastName, String PESEL, String parentFirstName, String parentLastName, String parentPhoneNumber, Address address, LocalDate createDate, LocalDateTime lastLoginTime, LocalDateTime updatedDate) {
        super(username, password, firstName, lastName, PESEL, createDate, updatedDate, lastLoginTime);
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.address = address;
    }

    public Student(String username, String password, String firstName, String lastName, String PESEL, String parentFirstName, String parentLastName, String parentPhoneNumber, Address address) {
        super(username, password, firstName, lastName, PESEL, null, null, null);
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.address =address;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
