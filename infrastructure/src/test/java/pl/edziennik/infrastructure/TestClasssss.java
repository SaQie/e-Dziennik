package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;


public class TestClasssss extends BaseIntegrationTest {

    @Autowired
    private TeacherCommandRepository teacherCommandRepository;

    @Test
    public void run() {
        teacherCommandRepository.save(Teacher.of(null,null,null,null));
    }

}
