package pl.edziennik.eDziennik.domain.personinformation.services;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PersonInformationId;

public interface PersonInformationService {

    void update(final PersonInformationId personInformationId, final PersonInformation personInformation);

}
