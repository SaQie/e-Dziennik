package pl.edziennik.eDziennik.server.teacher.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.address.Address;
import pl.edziennik.eDziennik.server.basics.AbstractEntity;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.user.domain.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
    private Long id;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;


    @Override
    public boolean isNew() {
        return (id == null);
    }


    public Teacher(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSchool(School school){
        this.school = school;
    }


}
