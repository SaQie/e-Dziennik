package pl.edziennik.domain.parent;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.personinfromation.PersonInformation;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@IdClass(ParentId.class)
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_id_seq")
    @SequenceGenerator(name = "parent_id_seq", sequenceName = "parent_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Embedded
    private PersonInformation personInformation;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
        student.setParent(this);
        student.setHasParentAccount(true);
    }

    public void clearStudent() {
        this.student.setParent(null);
        this.student = null;
    }

    public ParentId getParentId() {
        return ParentId.wrap(id);
    }

    public Parent(PersonInformation personInformation, Address address) {
        this.personInformation = personInformation;
        this.address = address;
    }

}
