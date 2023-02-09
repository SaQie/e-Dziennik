package pl.edziennik.eDziennik.server.student.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.student.domain.Student;

public interface StudentDao extends IBaseDao<Student> {

    Student getByUsername(String username);

    boolean isStudentExists(String username);

    boolean isStudentExistsByEmail(String email);

    boolean isStudentExistsByPesel(String pesel);
}
