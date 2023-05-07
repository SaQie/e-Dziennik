package pl.edziennik.domain.student;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.personinfromation.PersonInformation;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.user.User;


@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(StudentId.class)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
    @SequenceGenerator(name = "student_id_seq", sequenceName = "student_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private boolean hasParentAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @Embedded
    private PersonInformation personInformation;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Parent parent;

    public StudentId getStudentId() {
        return StudentId.wrap(id);
    }

    public Student(PersonInformation personInformation, Address address) {
        this.personInformation = personInformation;
        this.address = address;
    }


}
