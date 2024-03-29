package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;

import java.util.HashMap;
import java.util.Map;

public class AdminCommandMockRepo implements AdminCommandRepository {

    private final Map<AdminId, Admin> database;

    public AdminCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean isAdminAccountAlreadyExists() {
        return database.values().size() >= 1;

    }

    @Override
    public Admin save(Admin admin) {
        database.put(admin.adminId(), admin);
        return database.get(admin.adminId());
    }

}