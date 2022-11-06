package pl.edziennik.eDziennik.server.student.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.student.domain.Student;

public interface StudentDao extends IBaseDao<Student> {

    Student findByUsername(String username);

}