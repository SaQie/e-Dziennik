package pl.edziennik.eDziennik.server.subject.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

public interface SubjectDao extends IBaseDao<Subject> {

    boolean isSubjectAlreadyExist(String name, Long idSchoolClass);

}
