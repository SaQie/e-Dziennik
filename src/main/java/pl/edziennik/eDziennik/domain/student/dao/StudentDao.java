package pl.edziennik.eDziennik.domain.student.dao;

import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

public interface StudentDao extends IBaseDao<Student> {

    Student getByUsername(String username);

    boolean isStudentExists(String username);

    boolean isStudentExistsByEmail(String email);

    boolean isStudentExistsByPesel(String pesel);
}
