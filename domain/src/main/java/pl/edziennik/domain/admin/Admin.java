package pl.edziennik.domain.admin;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.user.User;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Admin {

    @EmbeddedId
    private AdminId adminId = AdminId.create();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    public static Admin of(User user){
        Admin admin = new Admin();
        admin.user = user;

        return admin;
    }
}
