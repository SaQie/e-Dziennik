package pl.edziennik.eDziennik.server.teacher.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.address.Address;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonInformation personInformation;


    public Teacher(String username, String password,String email, String phoneNumber,PersonInformation personInformation, Address address, LocalDate createDate, LocalDateTime lastLoginTime, LocalDateTime updateDate) {
        super(username, password,createDate,updateDate, lastLoginTime, email);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.personInformation = personInformation;
    }

    public Teacher(String username, String password,String email, String phoneNumber,PersonInformation personInformation, Address address) {
        super(username, password, null, null, null, email);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.personInformation = personInformation;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setSchool(School school){
        this.school = school;
    }


}
