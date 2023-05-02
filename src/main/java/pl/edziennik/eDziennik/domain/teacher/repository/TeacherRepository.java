package pl.edziennik.eDziennik.domain.teacher.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, TeacherId> {


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.personInformation pi " +
            "JOIN t.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :#{#roleId.id()}")
    boolean isTeacherExistsByPesel(Pesel pesel, RoleId roleId);

    boolean existsByUserUsername(String username);

    Teacher getByUserUsername(String username);

    @Query("SELECT s FROM Subject s where s.teacher.id = :#{#teacherId.id()}")
    List<Subject> getTeacherSubjectList(TeacherId teacherId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "school",
                    "address",
                    "user",
                    "personInformation"
            }
    )
    Page<Teacher> findAll(Pageable pageable);


}
