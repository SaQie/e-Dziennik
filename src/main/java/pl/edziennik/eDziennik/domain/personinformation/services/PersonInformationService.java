package pl.edziennik.eDziennik.domain.personinformation.services;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;

public interface PersonInformationService {

    void update(Long id, PersonInformation personInformation);

}
