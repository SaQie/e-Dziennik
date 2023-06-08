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
            @AttributeOverride(name = "value", column = @Column(name = "username", nullable = false))
    })
    private Username username;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    })
    private Password password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false))
    })
    private Email email;

    private Boolean isActive;

    @Column(nullable = false)
    private LocalDate createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime lastLoginDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role role;

    @Version
    private Long version;

    public static User of(Username username, Password password, Email email, Role role) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.email = email;
        user.role = role;
        user.createdDate = LocalDate.now();
        user.isActive = Boolean.FALSE;

        return user;
    }

    public void activate() {
        if (Boolean.FALSE.equals(isActive)) {
            isActive = Boolean.TRUE;
        }
    }

    public void unactivate() {
        if (Boolean.TRUE.equals(isActive)) {
            isActive = Boolean.FALSE;
        }
    }

    public void changeUserData(Username username, Email email) {
        if (username != null) {
            this.username = username;
        }

        if (email != null) {
            this.email = email;
        }
    }

    public void changePassword(Password password) {
        this.password = password;
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
