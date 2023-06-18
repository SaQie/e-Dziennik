package pl.edziennik.infrastructure.repository.director;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.director.DetailedDirectorDto;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.domain.director.Director;

@RepositoryDefinition(domainClass = Director.class, idClass = DirectorId.class)
public interface DirectorQueryRepository {


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.director.DetailedDirectorDto(d.directorId, u.username, u.email," +
            " d.personInformation.fullName, a.address, a.postalCode, a.city, u.pesel," +
            "d.personInformation.phoneNumber, s.schoolId, s.name) " +
            "FROM Director d " +
            "JOIN d.user u " +
            "JOIN d.school s " +
            "JOIN d.address a " +
            "WHERE d.directorId = :directorId")
    DetailedDirectorDto getDirector(DirectorId directorId);

}
