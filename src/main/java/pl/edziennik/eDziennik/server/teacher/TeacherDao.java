package pl.edziennik.eDziennik.server.teacher;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

public interface TeacherDao extends IBaseDao<Teacher> {

    Teacher findByUsername(String username);


}
