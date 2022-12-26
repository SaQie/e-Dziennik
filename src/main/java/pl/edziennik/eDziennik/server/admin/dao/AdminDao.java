package pl.edziennik.eDziennik.server.admin.dao;

import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.student.domain.Student;

public interface AdminDao extends IBaseDao<Admin> {

    Admin getByUsername(String username);

}
