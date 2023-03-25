package pl.edziennik.eDziennik.domain.schoolclass.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    boolean existsByClassNameAndSchoolId(String className, Long idSchool);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Teacher t WHERE t.id = :idTeacher " +
            "AND t.school.id = :idSchool")
    boolean existsByTeacherIdAndSchoolId(Long idTeacher, Long idSchool);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE sc.teacher.id = :idTeacher")
    boolean existsByTeacherId(Long idTeacher);

    @Query("SELECT sc.className FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE sc.teacher.id = :idTeacher")
    String getSchoolClassNameBySupervisingTeacherId(Long idTeacher);

    @Query(value = "SELECT sc FROM SchoolClass sc " +
            "JOIN FETCH sc.school " +
            "JOIN FETCH sc.teacher " +
            "WHERE sc.school.id = :idSchool",
            countQuery = "SELECT COUNT(sc) FROM SchoolClass sc where sc.school.id = :idSchool")
    Page<SchoolClass> findSchoolClassesBySchoolId(Pageable pageable, Long idSchool);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "school",
                    "teacher"
            }
    )
    Page<SchoolClass> findAll(Pageable pageable);
}
