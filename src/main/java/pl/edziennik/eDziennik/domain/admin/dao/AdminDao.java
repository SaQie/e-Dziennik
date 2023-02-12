package pl.edziennik.eDziennik.domain.admin.dao;

import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

public interface AdminDao extends IBaseDao<Admin> {

    Admin getByUsername(String username);

}
