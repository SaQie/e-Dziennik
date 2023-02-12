package pl.edziennik.eDziennik.domain.teacher.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;

public interface TeacherDao extends IBaseDao<Teacher> {

    Teacher getByUsername(String username);

    boolean isTeacherExist(String username);

    boolean isTeacherExistByPesel(String pesel);

}
