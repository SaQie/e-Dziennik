package pl.edziennik.eDziennik.domain.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.user.domain.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", sequenceName = "admin_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public AdminId getAdminId(){
        return AdminId.wrap(id);
    }

    public Admin(User user) {
        this.user = user;
    }

}
