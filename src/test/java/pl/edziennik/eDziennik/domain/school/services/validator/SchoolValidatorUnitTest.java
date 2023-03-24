package pl.edziennik.eDziennik.domain.school.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchoolValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private SchoolAlreadyExistsValidator existsValidator;

    @InjectMocks
    private SchoolNipAlreadyExistsValidator nipAlreadyExistsValidator;

    @InjectMocks
    private SchoolRegonAlreadyExistValidator regonAlreadyExistValidator;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenSchoolWithGivenNipAlreadyExists() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByNip("89234")).thenReturn(true);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, "89234"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = nipAlreadyExistsValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolValidators.SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolRequestApiDto.NIP);
        assertEquals(error.getCause(), getErrorMessage(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST));
    }

    @Test
    public void shouldReturnApiErrorWhenSchoolWithGivenRegonAlreadyExists() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByRegon("5352")).thenReturn(true);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST, "5352"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = regonAlreadyExistValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolValidators.SCHOOL_REGON_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolRequestApiDto.REGON);
        assertEquals(error.getCause(), getErrorMessage(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST));
    }

    @Test
    public void shouldReturnApiErrorWhenSchoolWithGivenNameAlreadyExists() {
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByName("asdasd")).thenReturn(true);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, "asdasd"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existsValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolValidators.SCHOOL_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolRequestApiDto.NAME);
        assertEquals(error.getCause(), getErrorMessage(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenSchoolWithGivenNameNotExists(){
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByName("asdasd")).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, "asdasd"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existsValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenSchoolWithGivenRegonNotExists(){
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByRegon("5352")).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST, "5352"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = regonAlreadyExistValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenSchoolWithGivenNipNotExists(){
        // given
        SchoolRequestApiDto dto = schoolUtil.prepareSchoolRequestApi();

        when(schoolRepository.existsByNip("89234")).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, "89234"))
                .thenReturn(SchoolValidators.EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = nipAlreadyExistsValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }




}
