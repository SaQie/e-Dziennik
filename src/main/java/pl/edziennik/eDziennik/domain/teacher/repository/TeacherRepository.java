package pl.edziennik.eDziennik.domain.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.personInformation pi " +
            "JOIN t.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :idRole")
    boolean isTeacherExistsByPesel(String pesel, Long idRole);

    boolean existsByUserUsername(String username);

    Teacher getByUserUsername(String username);

}
