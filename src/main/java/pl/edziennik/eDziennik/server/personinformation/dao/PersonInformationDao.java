package pl.edziennik.eDziennik.server.personinformation.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;

public interface PersonInformationDao extends IBaseDao<PersonInformation> {

    boolean isPeselAlreadyExist(String pesel);
}
