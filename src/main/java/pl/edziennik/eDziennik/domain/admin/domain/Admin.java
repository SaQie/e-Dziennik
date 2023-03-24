package pl.edziennik.eDziennik.domain.admin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;
import pl.edziennik.eDziennik.domain.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", sequenceName = "admin_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean isNew() {
        return (id == null);
    }

    public Admin(User user) {
        this.user = user;
    }

}
