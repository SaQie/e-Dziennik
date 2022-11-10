package pl.edziennik.eDziennik.server.teacher.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends BasicUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
    private Long id;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;



    public Teacher(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String phoneNumber, LocalDate createDate, LocalDateTime lastLoginTime, LocalDateTime updateDate) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city, createDate,updateDate, lastLoginTime);
        this.phoneNumber = phoneNumber;
    }

    public Teacher(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String phoneNumber) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city, null, null, null);
        this.phoneNumber = phoneNumber;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setSchool(School school){
        this.school = school;
    }


}
