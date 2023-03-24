package pl.edziennik.eDziennik.domain.schoolclass.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

    @Query("SELECT sc.className FROM SchoolClass sc JOIN sc.teacher t WHERE sc.teacher.id = :idTeacher")
    String getSchoolClassNameBySupervisingTeacherId(Long idTeacher);

    Page<SchoolClass> findSchoolClassesBySchoolId(Pageable pageable, Long idSchool);
}
