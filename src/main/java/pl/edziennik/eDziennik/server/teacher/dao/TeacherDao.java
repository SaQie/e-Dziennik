package pl.edziennik.eDziennik.server.teacher.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.Optional;

public interface TeacherDao extends IBaseDao<Teacher> {

    Teacher getByUsername(String username);

    boolean isTeacherExist(String username);

    boolean isTeacherExistByPesel(String pesel);

}
