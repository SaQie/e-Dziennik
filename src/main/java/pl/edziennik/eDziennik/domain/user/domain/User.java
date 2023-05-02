package pl.edziennik.eDziennik.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

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

    private String username;
    private String password;
    private LocalDate createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginDate;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Role role;

    public User(String username, String password, String email) {
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
