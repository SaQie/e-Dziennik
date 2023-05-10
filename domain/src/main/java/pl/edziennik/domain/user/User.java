package pl.edziennik.domain.user;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.role.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId userId = UserId.create();

    private UUID superId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "username"))
    })
    private Username username;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "password"))
    })
    private Password password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;


    private LocalDate createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Role role;

    public static User of(Username username, Password password, Email email, Role role) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.email = email;
        user.role = role;
        user.createdDate = LocalDate.now();

        return user;
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
