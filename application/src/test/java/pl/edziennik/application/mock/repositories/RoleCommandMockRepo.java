package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.repositories.role.RoleCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleCommandMockRepo implements RoleCommandRepository {

    private final Map<RoleId, Role> database;

    public static final RoleId ADMIN_ROLE = RoleId.create();
    public static final RoleId TEACHER_ROLE = RoleId.create();
    public static final RoleId STUDENT_ROLE = RoleId.create();
    public static final RoleId PARENT_ROLE = RoleId.create();

    public static final Name ADMIN_ROLE_NAME = Name.of("ROLE_ADMIN");
    public static final Name STUDENT_ROLE_NAME = Name.of("ROLE_STUDENT");
    public static final Name TEACHER_ROLE_NAME = Name.of("ROLE_TEACHER");
    public static final Name PARENT_ROLE_NAME = Name.of("ROLE_PARENT");

    public RoleCommandMockRepo() {
        this.database = new HashMap<>();
        this.database.put(ADMIN_ROLE, Role.of(ADMIN_ROLE_NAME));
        this.database.put(STUDENT_ROLE, Role.of(STUDENT_ROLE_NAME));
        this.database.put(TEACHER_ROLE, Role.of(TEACHER_ROLE_NAME));
        this.database.put(PARENT_ROLE, Role.of(PARENT_ROLE_NAME));
    }


    @Override
    public Role getByName(Name role) {
        List<Role> roles = database.values().stream()
                .filter(item -> item.getName().equals(role))
                .toList();
        if (roles.isEmpty()) {
            return null;
        }
        return roles.get(0);
    }
}
