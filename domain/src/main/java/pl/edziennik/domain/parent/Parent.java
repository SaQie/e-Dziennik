package pl.edziennik.domain.parent;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parent {

    @EmbeddedId
    private ParentId parentId = ParentId.create();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Embedded
    private PersonInformation personInformation;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


    public static Parent of(User user, PersonInformation personInformation, Address address, Student student) {
        Parent parent = of(user, personInformation, address);
        parent.student = student;

        return parent;
    }

    public static Parent of(User user, PersonInformation personInformation, Address address) {
        Parent parent = new Parent();
        parent.address = address;
        parent.personInformation = personInformation;
        parent.user = user;

        return parent;
    }

}
