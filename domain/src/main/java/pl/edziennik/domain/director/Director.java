package pl.edziennik.domain.director;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Director {

    @EmbeddedId
    private DirectorId directorId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
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
    public static Director of(DirectorId directorId, User user, PersonInformation personInformation, Address address, School school) {
        Director director = new Director();

        director.school = school;
        director.address = address;
        director.personInformation = personInformation;
        director.user = user;
        director.directorId = directorId;

        school.assignDirector(director);

        return director;
    }
}
