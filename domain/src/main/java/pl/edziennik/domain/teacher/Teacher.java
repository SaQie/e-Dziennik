package pl.edziennik.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Teacher {

    @EmbeddedId
    private TeacherId teacherId = TeacherId.create();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @Embedded
    @Column(nullable = false)
    private PersonInformation personInformation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Version
    private Long version;


    public static Teacher of(User user, School school, PersonInformation personInformation, Address address) {
        Teacher teacher = new Teacher();
        teacher.school = school;
        teacher.address = address;
        teacher.personInformation = personInformation;
        teacher.user = user;

        return teacher;
    }

}
