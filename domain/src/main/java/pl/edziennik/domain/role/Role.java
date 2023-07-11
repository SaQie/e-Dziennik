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
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Version
    private Long version;

    public static Role of(Name name) {
        Role role = new Role();
        role.name = name;

        return role;
    }

    public static Role of(RoleId roleId,Name name) {
        Role role = new Role();
        role.name = name;
        role.roleId = roleId;

        return role;
    }

}
