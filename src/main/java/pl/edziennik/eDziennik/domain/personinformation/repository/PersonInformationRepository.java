package pl.edziennik.eDziennik.domain.personinformation.repository;

//@Repository
public interface PersonInformationRepository {

    boolean existsByPesel(String pesel);

}
