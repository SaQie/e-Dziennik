package pl.edziennik.eDziennik.domain.school.dao;

import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

public interface SchoolDao extends IBaseDao<School> {

    boolean isSchoolExist(String schoolName);

    boolean isSchoolWithNipExist(String nip);

    boolean isSchoolWithRegonExist(String regon);
}
