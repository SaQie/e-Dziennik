package pl.edziennik.eDziennik.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.services.SchoolService;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentIntegrationTest extends BaseTest {

    @Autowired
    private SchoolService service;

    @BeforeEach
    void prepareDb(){
        clearDb();
        fillDbWithData();
    }

    @Test
    public void testtt(){
        SchoolLevel schoolClass = find(SchoolLevel.class, 1L);

        List<SchoolResponseApiDto> allSchools = service.findAllSchools();


        Assertions.assertNotNull(schoolClass);
        Assertions.assertNotNull(allSchools);
    }

}
