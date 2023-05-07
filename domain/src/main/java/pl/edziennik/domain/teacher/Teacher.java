package pl.edziennik.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.personinfromation.PersonInformation;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;

@Entity
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@IdClass(TeacherId.class)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @Embedded
    private PersonInformation personInformation;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    public Teacher(PersonInformation personInformation, Address address) {
        this.personInformation = personInformation;
        this.address = address;
    }

    public TeacherId getTeacherId() {
        return TeacherId.wrap(id);
    }

}
