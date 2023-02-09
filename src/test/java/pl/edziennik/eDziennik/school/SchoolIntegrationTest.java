package pl.edziennik.eDziennik.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.services.SchoolService;
import pl.edziennik.eDziennik.server.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.validator.TeacherValidators;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getNip(), actual.getNip());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getPostalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.getRegon(), actual.getRegon());
        assertEquals(expected.getIdSchoolLevel(), actual.getSchoolLevel().getId());
    }

    @Test
    public void shouldUpdateSchool() {
        // given
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi();
        Long id = service.createNewSchool(dto).getId();
        SchoolRequestApiDto expected = util.prepareSchoolRequestApi("afterEdit", "555555", "555555");

        // when
        Long updated = service.updateSchool(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        School actual = find(School.class, updated);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getNip(), actual.getNip());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getPostalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.getRegon(), actual.getRegon());
        assertEquals(expected.getIdSchoolLevel(), actual.getSchoolLevel().getId());

    }

    @Test
    public void shouldThrowsExceptionWhenSchoolLevelNotExist() {
        // given
        Long idSchoolLevel = 99L;
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi(idSchoolLevel);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.createNewSchool(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolLevel, SchoolLevel.class.getSimpleName()));
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
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getNip(), actual.getNip());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getRegon(), actual.getRegon());
        assertEquals(expected.getIdSchoolLevel(), actual.getSchoolLevel().getId());
    }

    @Test
    public void shouldThrowsExceptionWhenDeleteIfSchoolNotExist() {
        // given
        Long idSchool = 99L;
        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.deleteSchoolById(idSchool));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenSchoolAlreadyExist() {
        // given
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi();
        // first school
        service.createNewSchool(dto);

        // second school with the same name
        SchoolRequestApiDto dto2 = util.prepareSchoolRequestApi("asdasd", "1111", "2222");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createNewSchool(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolValidators.SCHOOL_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolRequestApiDto.NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, dto2.getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());


    }


    @Test
    public void shouldThrowsBusinessExceptionWhenSchoolWithRegonAlreadyExist() {
        // given
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi();
        // first school
        service.createNewSchool(dto);

        // second school with the same regon number
        SchoolRequestApiDto dto2 = util.prepareSchoolRequestApi("test2", "12313", "5352");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createNewSchool(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolValidators.SCHOOL_REGON_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolRequestApiDto.REGON, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST, dto2.getRegon());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenSchoolWithNipAlreadyExist() {
        // given
        SchoolRequestApiDto dto = util.prepareSchoolRequestApi();
        // first school
        service.createNewSchool(dto);

        // second school with the same nip number
        SchoolRequestApiDto dto2 = util.prepareSchoolRequestApi("test2", "89234", "511352");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createNewSchool(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolValidators.SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolRequestApiDto.NIP, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto2.getNip());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

}
