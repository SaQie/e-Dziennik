package pl.edziennik.domain.admin;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.user.User;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @EmbeddedId
    private AdminId adminId = AdminId.create();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Version
    private Long version;


    @Builder
    public static Admin of(User user){
        Admin admin = new Admin();

        admin.user = user;

        return admin;
    }
}
