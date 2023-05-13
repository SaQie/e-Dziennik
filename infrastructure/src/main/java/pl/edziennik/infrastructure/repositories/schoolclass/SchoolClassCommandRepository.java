package pl.edziennik.infrastructure.repositories.schoolclass;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;

import java.util.Optional;

@RepositoryDefinition(domainClass = SchoolClass.class, idClass = SchoolClassId.class)
public interface SchoolClassCommandRepository {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM School s " +
            "JOIN SchoolClass sc ON s.schoolId = sc.school.schoolId " +
            "WHERE s.schoolId = :schoolId AND sc.schoolClassId = :schoolClassId")
    boolean isSchoolClassBelongToSchool(SchoolClassId schoolClassId, SchoolId schoolId);

    Optional<SchoolClass> findById(SchoolClassId schoolClassId);

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END " +
            "FROM School s " +
            "JOIN s.schoolClasses sc " +
            "WHERE sc.className = :name " +
            "AND s.schoolId = :schoolId")
    boolean existsByName(Name name, SchoolId schoolId);

    SchoolClass save(SchoolClass schoolClass);

    SchoolClass getReferenceById(SchoolClassId schoolClassId);
}
