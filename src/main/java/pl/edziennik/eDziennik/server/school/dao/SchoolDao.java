package pl.edziennik.eDziennik.server.school.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;

public interface SchoolDao extends IBaseDao<School> {

    boolean isSchoolExist(String schoolName);

    boolean isSchoolWithNipExist(String nip);

    boolean isSchoolWithRegonExist(String regon);
}
