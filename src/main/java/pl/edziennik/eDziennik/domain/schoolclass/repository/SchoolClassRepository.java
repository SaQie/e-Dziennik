package pl.edziennik.eDziennik.domain.schoolclass.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, SchoolClassId> {

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc WHERE sc.school.id = :#{#schoolId.id()} " +
            "AND sc.className = :className")
    boolean existsByClassNameAndSchoolId(String className, SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Teacher t WHERE t.id = :#{#teacherId.id()} " +
            "AND t.school.id = :#{#schoolId.id()}")
    boolean existsByTeacherIdAndSchoolId(TeacherId teacherId, SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE sc.teacher.id = :#{#teacherId.id()}")
    boolean existsByTeacherId(TeacherId teacherId);

    @Query("SELECT sc.className FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE sc.teacher.id = :#{#teacherId.id()}")
    String getSchoolClassNameBySupervisingTeacherId(TeacherId teacherId);

    @Query(value = "SELECT sc FROM SchoolClass sc " +
            "JOIN FETCH sc.school " +
            "JOIN FETCH sc.teacher " +
            "WHERE sc.school.id = :schoolId",
            countQuery = "SELECT COUNT(sc) FROM SchoolClass sc where sc.school.id = :#{schoolId.id()}")
    Page<SchoolClass> findSchoolClassesBySchoolId(Pageable pageable, SchoolId schoolId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "school",
                    "teacher"
            }
    )
    Page<SchoolClass> findAll(Pageable pageable);
}
