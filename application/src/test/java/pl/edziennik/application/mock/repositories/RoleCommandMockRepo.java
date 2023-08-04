package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleCommandMockRepo implements RoleCommandRepository {

    private final Map<RoleId, Role> database;

    public static final Name ADMIN_ROLE_NAME = Name.of("ROLE_ADMIN");
    public static final Name STUDENT_ROLE_NAME = Name.of("ROLE_STUDENT");
    public static final Name TEACHER_ROLE_NAME = Name.of("ROLE_TEACHER");
    public static final Name PARENT_ROLE_NAME = Name.of("ROLE_PARENT");
    public static final Name DIRECTOR_ROLE_NAME = Name.of("ROLE_DIRECTOR");

    public RoleCommandMockRepo() {
        this.database = new HashMap<>();
        this.database.put(RoleId.PredefinedRow.ROLE_ADMIN, Role.of(RoleId.PredefinedRow.ROLE_ADMIN, ADMIN_ROLE_NAME));
        this.database.put(RoleId.PredefinedRow.ROLE_STUDENT, Role.of(RoleId.PredefinedRow.ROLE_STUDENT, STUDENT_ROLE_NAME));
        this.database.put(RoleId.PredefinedRow.ROLE_TEACHER, Role.of(RoleId.PredefinedRow.ROLE_TEACHER, TEACHER_ROLE_NAME));
        this.database.put(RoleId.PredefinedRow.ROLE_PARENT, Role.of(RoleId.PredefinedRow.ROLE_PARENT, PARENT_ROLE_NAME));
        this.database.put(RoleId.PredefinedRow.ROLE_DIRECTOR, Role.of(RoleId.PredefinedRow.ROLE_DIRECTOR, DIRECTOR_ROLE_NAME));
    }


    @Override
    public Role getByName(Name role) {
        List<Role> roles = database.values().stream()
                .filter(item -> item.name().equals(role))
                .toList();
        if (roles.isEmpty()) {
            return null;
        }
        return roles.get(0);
    }

    @Override
    public Role getByRoleId(RoleId roleId) {
        return database.get(roleId);
    }
}
