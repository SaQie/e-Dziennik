package pl.edziennik.eDziennik.domain.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.services.SchoolService;
import pl.edziennik.eDziennik.domain.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SchoolIntegrationTest extends BaseTest {
    @Test
    public void shouldSaveNewSchool() {
        // given
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi();

        // when
        Long id = schoolService.createNewSchool(expected).getId();

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
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        Long id = schoolService.createNewSchool(dto).getId();
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi("afterEdit", "555555", "555555");

        // when
        Long updated = schoolService.updateSchool(id, expected).getId();

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
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi(idSchoolLevel);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolService.createNewSchool(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolLevel, SchoolLevel.class.getSimpleName()));
    }

    @Test
    public void shouldReturnSchoolWithGivenId() {
        // given
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi();
        Long id = schoolService.createNewSchool(expected).getId();

        // when
        SchoolResponseApiDto actual = schoolService.findSchoolById(id);

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
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolService.deleteSchoolById(idSchool));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenSchoolAlreadyExist() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        // first school
        schoolService.createNewSchool(dto);

        // second school with the same name
        SchoolRequestApiDto dto2 = schoolUtil.prepareSchoolRequestApi("asdasd", "1111", "2222");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolService.createNewSchool(dto2));

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
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        // first school
        schoolService.createNewSchool(dto);

        // second school with the same regon number
        SchoolRequestApiDto dto2 = schoolUtil.prepareSchoolRequestApi("test2", "12313", "5352");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolService.createNewSchool(dto2));

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
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        // first school
        schoolService.createNewSchool(dto);

        // second school with the same nip number
        SchoolRequestApiDto dto2 = schoolUtil.prepareSchoolRequestApi("test2", "89234", "511352");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolService.createNewSchool(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolValidators.SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolRequestApiDto.NIP, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto2.getNip());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

}
