package pl.edziennik.eDziennik.server.basics.test;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;

import javax.persistence.EntityManager;

@Service
@AllArgsConstructor
public class TestService {

    private final EntityManager entityManager;

    public void test(){
        entityManager.find(SchoolLevel.class, 1L);
    }
}
