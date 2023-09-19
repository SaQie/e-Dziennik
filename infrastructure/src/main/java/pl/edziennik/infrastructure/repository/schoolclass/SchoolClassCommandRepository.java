package pl.edziennik.infrastructure.repository.schoolclass;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;

import java.util.List;
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

    SchoolClass getBySchoolClassId(SchoolClassId schoolClassId);

    @Query("SELECT sc FROM SchoolClass sc " +
            "LEFT JOIN FETCH sc.subjects " +
            "WHERE sc.schoolClassId = :schoolClassId")
    SchoolClass getBySchoolClassIdWithSubjects(SchoolClassId schoolClassId);

    @Query("SELECT CASE WHEN COUNT(s) >= scc.maxStudentsSize THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.schoolClassConfiguration scc " +
            "LEFT JOIN sc.students s " +
            "WHERE sc.schoolClassId = :schoolClassId " +
            "GROUP BY scc.maxStudentsSize ")
    boolean isStudentLimitReached(SchoolClassId schoolClassId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.students s " +
            "WHERE sc.schoolClassId = :schoolClassId")
    boolean isStudentsExists(SchoolClassId schoolClassId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.subjects s " +
            "WHERE sc.schoolClassId = :schoolClassId")
    boolean isSubjectsExists(SchoolClassId schoolClassId);

    void deleteById(SchoolClassId schoolClassId);

    @Query("SELECT lp.lessonPlanId FROM LessonPlan lp " +
            "JOIN lp.schoolClass sc " +
            "WHERE sc.schoolClassId = :schoolClassId ")
    List<LessonPlanId> getLessonPlansIdsBySchoolClassId(SchoolClassId schoolClassId);
}
