package pl.edziennik.infrastructure.command.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.school.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassId;

@Repository
public interface SchoolClassCommandRepository extends JpaRepository<SchoolClass, SchoolClassId> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM School s " +
            "JOIN SchoolClass sc ON s.id = sc.school.id " +
            "WHERE s.id = :#{#schoolId.id()} AND sc.id = :#{#schoolClassId.id()}")
    boolean isSchoolClassBelongToSchool(SchoolClassId schoolClassId, SchoolId schoolId);

}
