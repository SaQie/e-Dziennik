package pl.edziennik.eDziennik.server.teacher.domain;

import lombok.*;
import pl.edziennik.eDziennik.server.basics.BasicEntity;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Teacher extends BasicUser implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "teacher")
    private Collection<Subject> subjects = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    public Teacher(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String phoneNumber) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city);
        this.phoneNumber = phoneNumber;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void setSchool(School school){
        this.school = school;
    }
    public void addSubject(Subject subject){
        subjects.add(subject);
        subject.setTeacher(this);
    }



}
