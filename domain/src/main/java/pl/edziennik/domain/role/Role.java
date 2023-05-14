package pl.edziennik.domain.role;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.RoleId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Role {

    @EmbeddedId
    private RoleId roleId = RoleId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private Name name;

    public static Role of(Name name) {
        Role role = new Role();
        role.name = name;

        return role;
    }

    public enum RoleConst {
        ROLE_ADMIN(Name.of("ROLE_ADMIN")),
        ROLE_TEACHER(Name.of("ROLE_TEACHER")),
        ROLE_STUDENT(Name.of("ROLE_STUDENT")),
        ROLE_PARENT(Name.of("ROLE_PARENT"));

        private final Name name;

        RoleConst(Name name) {
            this.name = name;
        }

        public Name roleName() {
            return name;
        }

    }

}
