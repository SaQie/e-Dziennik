package pl.edziennik.eDziennik.domain.personinformation.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PersonInformationId;
import pl.edziennik.eDziennik.domain.personinformation.repository.PersonInformationRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
class PersonInformationServiceImpl implements PersonInformationService {

    private final PersonInformationRepository repository;

    @Override
    @Transactional
    public void update(final PersonInformationId personInformationId,final PersonInformation personInformationSource) {
        Optional<PersonInformation> personInformationOptional = repository.findById(personInformationId.id());
        if (personInformationOptional.isPresent()) {
            PersonInformation personInformationToEdit = personInformationOptional.get();
            personInformationToEdit.setFirstName(personInformationSource.getFirstName());
            personInformationToEdit.setLastName(personInformationSource.getLastName());
            personInformationToEdit.setPhoneNumber(personInformationSource.getPhoneNumber());
            personInformationToEdit.setPesel(personInformationSource.getPesel());
        }

    }
}
