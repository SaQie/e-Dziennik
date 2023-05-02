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
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;
import pl.edziennik.eDziennik.server.exception.BusinessException;
import pl.edziennik.eDziennik.server.exception.EntityNotFoundException;

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
        SchoolId schoolId = schoolService.createNewSchool(expected).schoolId();

        // then
        assertNotNull(schoolId);
        School actual = find(School.class, schoolId);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.nip(), actual.getNip());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.postalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.regon(), actual.getRegon());
        assertEquals(expected.schoolLevelId(), actual.getSchoolLevel().getSchoolLevelId());
    }

    @Test
    public void shouldUpdateSchool() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();
        SchoolId id = schoolService.createNewSchool(dto).schoolId();
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi("afterEdit", "555555", "555555");

        // when
        SchoolId updated = schoolService.updateSchool(id, expected).schoolId();

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
        assertEquals(expected.schoolLevelId(), actual.getSchoolLevel().getSchoolLevelId());

    }

    @Test
    public void shouldThrowsExceptionWhenSchoolLevelNotExist() {
        // given
        Long idSchoolLevel = 99L;
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi(SchoolLevelId.wrap(idSchoolLevel));

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolService.createNewSchool(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolLevel, SchoolLevel.class.getSimpleName()));
    }

    @Test
    public void shouldReturnSchoolWithGivenId() {
        // given
        SchoolRequestApiDto expected = schoolUtil.prepareSchoolRequestApi();
        SchoolId id = schoolService.createNewSchool(expected).schoolId();

        // when
        SchoolResponseApiDto actual = schoolService.findSchoolById(id);

        // then
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.address(), actual.address());
        assertEquals(expected.nip(), actual.nip());
        assertEquals(expected.city(), actual.city());
        assertEquals(expected.postalCode(), actual.postalCode());
        assertEquals(expected.regon(), actual.regon());
        assertEquals(expected.schoolLevelId(), actual.schoolLevel().schoolLevelId());
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
