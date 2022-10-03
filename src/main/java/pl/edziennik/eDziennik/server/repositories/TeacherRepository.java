package pl.edziennik.eDziennik.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.teacher.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t, t.role from Teacher t join t.role r where t.username = :username")
    Teacher findByUsername(String username);


}
