package pl.edziennik.eDziennik.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.services.SchoolService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SchoolIntegrationTest extends BaseTest {

    @Autowired
    private SchoolService service;

    private SchoolIntegrationTestUtil util;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    public SchoolIntegrationTest() {
        this.util = new SchoolIntegrationTestUtil();
    }

    @Test
    public void shouldSaveNewSchool() {
        // given
        SchoolRequestApiDto expected = util.prepareSchoolRequestApi();

        // when
        Long id = service.createNewSchool(expected).getId();

        // then
        assertNotNull(id);
        School actual = find(School.class, id);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getNip(), actual.getNip());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getRegon(), actual.getRegon());
        assertEquals(expected.getSchoolLevel(), actual.getSchoolLevel().getId());
    }

    @Test
    public void shouldThrowsExceptionWhenSchoolLevelNotExist() {
        // given
        Long idSchoolLevel = 99L;
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi(idSchoolLevel);

        // when
        Throwable throwable = catchThrowable(() -> service.createNewSchool(dto));

        // then
        assertThat(throwable).hasMessageContaining("SchoolLevel with id " + idSchoolLevel + " not found");
    }

    @Test
    public void shouldReturnSchoolWithGivenId() {
        // given
        SchoolRequestApiDto expected = util.prepareSchoolRequestApi();
        Long id = service.createNewSchool(expected).getId();

        // when
        SchoolResponseApiDto actual = service.findSchoolById(id);

        // then
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getNip(), actual.getNip());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getRegon(), actual.getRegon());
        assertEquals(expected.getSchoolLevel(), actual.getIdSchoolLevel());
    }

    @Test
    public void shouldThrowsExceptionWhenDeleteIfSchoolNotExist() {
        // given
        Long idSchool = 99L;
        // when
        Throwable throwable = catchThrowable(() -> service.deleteSchoolById(idSchool));

        // then
        assertThat(throwable).hasMessageContaining("School with given id " + idSchool + " not exist");

    }

}
