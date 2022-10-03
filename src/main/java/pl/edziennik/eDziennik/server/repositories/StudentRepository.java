package pl.edziennik.eDziennik.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.student.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {


    Student findByUsername(String username);

}
