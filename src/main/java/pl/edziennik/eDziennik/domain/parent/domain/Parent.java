package pl.edziennik.eDziennik.domain.parent.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.user.domain.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Parent{

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

    public void clearStudent(){
        this.student.setParent(null);
        this.student = null;
    }

    public ParentId getParentId(){
        return ParentId.wrap(id);
    }

    public Parent(PersonInformation personInformation, Address address) {
        this.personInformation = personInformation;
        this.address = address;
    }

}
