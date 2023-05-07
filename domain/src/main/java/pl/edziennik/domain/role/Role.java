package pl.edziennik.domain.role;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@IdClass(RoleId.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Role(String name) {
        this.name = name;
    }

    public RoleId getRoleId() {
        return RoleId.wrap(id);
    }

    public enum RoleConst {
        ROLE_ADMIN(RoleId.wrap(1L)),
        ROLE_TEACHER(RoleId.wrap(2L)),
        ROLE_STUDENT(RoleId.wrap(3L)),
        ROLE_PARENT(RoleId.wrap(4L));

        private final RoleId id;

        RoleConst(RoleId id) {
            this.id = id;
        }

        public RoleId getId() {
            return id;
        }
    }

}
