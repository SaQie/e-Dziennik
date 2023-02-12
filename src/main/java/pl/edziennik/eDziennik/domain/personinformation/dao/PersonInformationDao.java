package pl.edziennik.eDziennik.domain.personinformation.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;

public interface PersonInformationDao extends IBaseDao<PersonInformation> {

    boolean isPeselAlreadyExist(String pesel);
}
