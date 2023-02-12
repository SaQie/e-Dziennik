package pl.edziennik.eDziennik.domain.subject.dao;

import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

public interface SubjectDao extends IBaseDao<Subject> {

    boolean isSubjectAlreadyExist(String name, Long idSchoolClass);

}
