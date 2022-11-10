package pl.edziennik.eDziennik.server.student.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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



    public Student(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String parentFirstName, String parentLastName, String parentPhoneNumber, LocalDate createDate, LocalDateTime lastLoginTime, LocalDateTime updatedDate) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city, createDate, updatedDate, lastLoginTime);
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public Student(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String parentFirstName, String parentLastName, String parentPhoneNumber) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city, null, null, null);
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
