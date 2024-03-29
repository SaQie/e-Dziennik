package pl.edziennik.domain.teacher;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class Teacher {

    @EmbeddedId
    private TeacherId teacherId;

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


    @Builder
    public static Teacher of(TeacherId teacherId, User user, School school, PersonInformation personInformation, Address address) {
        Teacher teacher = new Teacher();

        teacher.school = school;
        teacher.address = address;
        teacher.personInformation = personInformation;
        teacher.user = user;
        teacher.teacherId = teacherId;

        school.teachers().add(teacher);

        return teacher;
    }

}
