package pl.edziennik.domain.student;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.JournalNumber;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.user.User;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Student {

    @EmbeddedId
    private StudentId studentId = StudentId.create();

    private boolean hasParentAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "journal_number"))
    })
    private JournalNumber journalNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @Embedded
    private PersonInformation personInformation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private Parent parent;

    @Version
    private Long version;


    public static Student of(User user, School school, SchoolClass schoolClass, PersonInformation personInformation,
                             Address address) {
        Student student = new Student();
        student.address = address;
        student.personInformation = personInformation;
        student.school = school;
        student.schoolClass = schoolClass;
        student.user = user;

        school.getStudents().add(student);
        schoolClass.getStudents().add(student);

        return student;
    }

    public void assignParent(Parent parent) {
        this.parent = parent;
    }


}
