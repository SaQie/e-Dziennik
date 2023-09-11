package pl.edziennik.domain.parent;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Parent {

    @EmbeddedId
    private ParentId parentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Embedded
    private PersonInformation personInformation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Version
    private Long version;


    @Builder
    public static Parent of(ParentId parentId, User user, PersonInformation personInformation, Address address, Student student) {
        Parent parent = new Parent();

        parent.address = address;
        parent.personInformation = personInformation;
        parent.user = user;
        parent.student = student;
        parent.parentId = parentId;

        return parent;
    }

}
