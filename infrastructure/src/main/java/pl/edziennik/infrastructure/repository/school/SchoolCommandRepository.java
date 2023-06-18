package pl.edziennik.infrastructure.repository.school;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Nip;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;

import java.util.Optional;

@RepositoryDefinition(domainClass = School.class, idClass = SchoolId.class)
public interface SchoolCommandRepository {
    School getReferenceById(SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM School s " +
            "WHERE s.name = :name ")
    boolean isExistsByName(Name name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM School s " +
            "WHERE s.nip = :nip ")
    boolean isExistsByNip(Nip nip);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM School s " +
            "WHERE s.regon = :regon ")
    boolean isExistsByRegon(Regon regon);

    School save(School school);

    Optional<School> findById(SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.school s " +
            "WHERE s.schoolId = :schoolId")
    boolean isTeacherExistsInSchool(SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.school sc " +
            "WHERE sc.schoolId = :schoolId")
    boolean isStudentExistsInSchool(SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END FROM SchoolClass sc " +
            "JOIN sc.school s " +
            "WHERE s.schoolId = :schoolId")
    boolean isSchoolClassExistsInSchool(SchoolId schoolId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM School s " +
            "WHERE s.director IS NOT NULL " +
            "AND s.schoolId = :schoolId")
    boolean isSchoolHasAssignedDirector(SchoolId schoolId);

    void deleteById(SchoolId schoolId);

    School getBySchoolId(SchoolId schoolId);
}
