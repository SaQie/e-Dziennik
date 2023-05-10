package pl.edziennik.domain.student;

import jakarta.persistence.*;
import lombok.*;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private Parent parent;


    public static Student of(User user, School school, SchoolClass schoolClass, PersonInformation personInformation,
                             Address address) {
        Student student = new Student();
        student.address = address;
        student.personInformation = personInformation;
        student.school = school;
        student.schoolClass = schoolClass;
        student.user = user;

        return student;
    }

    public void assignParent(Parent parent){
        this.parent = parent;
    }


}
