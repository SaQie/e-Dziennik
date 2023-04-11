package pl.edziennik.eDziennik.domain.school;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SchoolIntegrationTest extends BaseTesting {
    @Test
    public void shouldSaveNewSchool() {
        // given
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi();

        // when
        Long id = schoolService.createNewSchool(expected).id();

        // then
        assertNotNull(id);
        School actual = find(School.class, id);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.nip(), actual.getNip());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.postalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.regon(), actual.getRegon());
        assertEquals(expected.idSchoolLevel(), actual.getSchoolLevel().getSchoolLevelId().id());
    }

    @Test
    public void shouldUpdateSchool() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        Long id = schoolService.createNewSchool(dto).id();
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi("afterEdit", "555555", "555555");

        // when
        Long updated = schoolService.updateSchool(SchoolId.wrap(id), expected).id();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        School actual = find(School.class, updated);
        assertNotNull(actual);
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.nip(), actual.getNip());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.postalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.regon(), actual.getRegon());
        assertEquals(expected.idSchoolLevel(), actual.getSchoolLevel().getSchoolLevelId().id());

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
        Long id = schoolService.createNewSchool(expected).id();

        // when
        SchoolResponseApiDto actual = schoolService.findSchoolById(SchoolId.wrap(id));

        // then
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.address(), actual.address());
        assertEquals(expected.nip(), actual.nip());
        assertEquals(expected.city(), actual.city());
        assertEquals(expected.postalCode(), actual.postalCode());
        assertEquals(expected.regon(), actual.regon());
        assertEquals(expected.idSchoolLevel(), actual.schoolLevel().id());
    }

    @Test
    public void shouldThrowsExceptionWhenDeleteIfSchoolNotExist() {
        // given
        Long idSchool = 99L;
        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolService.deleteSchoolById(SchoolId.wrap(idSchool)));

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
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, dto2.name());
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
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST, dto2.regon());
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
        String expectedExceptionMessage = resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto2.nip());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

}
