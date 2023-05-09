package pl.edziennik.domain.role;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.common.valueobject.Name;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private Name name;

    public Role(Long id, Name name) {
        this.id = id;
        this.name = name;
    }


    public RoleId getRoleId() {
        return RoleId.wrap(id);
    }

    public enum RoleConst {
        ROLE_ADMIN(RoleId.wrap(1L), Name.of("ROLE_ADMIN")),
        ROLE_TEACHER(RoleId.wrap(2L), Name.of("ROLE_TEACHER")),
        ROLE_STUDENT(RoleId.wrap(3L), Name.of("ROLE_STUDENT")),
        ROLE_PARENT(RoleId.wrap(4L), Name.of("ROLE_PARENT"));

        private final RoleId id;
        private final Name name;

        RoleConst(RoleId id, Name name) {
            this.id = id;
            this.name = name;
        }

        public Name roleName() {
            return name;
        }

        public RoleId id() {
            return id;
        }
    }

}
