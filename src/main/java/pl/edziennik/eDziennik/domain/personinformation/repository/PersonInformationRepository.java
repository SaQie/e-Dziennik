package pl.edziennik.eDziennik.domain.personinformation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;

@Repository
public interface PersonInformationRepository extends JpaRepository<PersonInformation, Long> {

    boolean existsByPesel(String pesel);

}
