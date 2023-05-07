package pl.edziennik.eDziennik.infrastructure.repository.query.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;

@Repository
public interface SchoolClassQueryRepository extends JpaRepository<SchoolClass, SchoolClassId> {


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM School s " +
            "JOIN SchoolClass sc ON s.id = sc.school.id " +
            "WHERE s.id = :#{#schoolId.id()} AND sc.id = :#{#schoolClassId.id()}")
    boolean isSchoolClassBelongToSchool(SchoolClassId schoolClassId, SchoolId schoolId);

}
