package pl.edziennik.domain.user;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.domain.role.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@IdClass(UserId.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private Long superId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "username"))
    })
    private Regon username;


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

    public User(Regon username, Password password, Email email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserId getUserId() {
        return UserId.wrap(id);
    }


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
