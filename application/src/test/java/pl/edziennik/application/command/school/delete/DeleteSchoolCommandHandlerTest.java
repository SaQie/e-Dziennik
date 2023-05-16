package pl.edziennik.application.command.school.delete;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.domain.school.School;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteSchoolCommandHandlerTest extends BaseUnitTest {

    private final DeleteSchoolCommandHandler handler;

    public DeleteSchoolCommandHandlerTest() {
        this.handler = new DeleteSchoolCommandHandler(schoolCommandRepository);
    }


    @Test
    public void shouldDeleteSchool() {
        // given
        School school = createSchool("Test", "123123", "123123", address);
        school = schoolCommandRepository.save(school);

        assertNotNull(schoolCommandRepository.getReferenceById(school.getSchoolId()));

        // when
        schoolCommandRepository.deleteById(school.getSchoolId());

        // then
        assertNull(schoolCommandRepository.getReferenceById(school.getSchoolId()));
    }


}
