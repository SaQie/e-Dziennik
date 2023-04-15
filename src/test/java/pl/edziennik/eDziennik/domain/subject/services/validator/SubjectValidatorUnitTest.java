package pl.edziennik.eDziennik.domain.subject.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectValidatorUnitTest extends BaseUnitTest {


    @InjectMocks
    private SubjectAlreadyExistValidator validator;

    @Mock
    private SubjectRepository repository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenSubjectAlreadyExists() {
        // given
        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto("Przyroda", null, SchoolClassId.wrap(1L));
        when(repository.existsByNameAndSchoolClassId("Przyroda", 1L)).thenReturn(true);
        when(schoolClassRepository.findById(SchoolClassId.wrap(1L))).thenReturn(Optional.of(new SchoolClass()));
        lenient().when(resourceCreator.of(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.name(), null))
                .thenReturn(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SubjectValidators.SUBJECT_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), SubjectRequestApiDto.NAME);
        assertEquals(error.getCause(), getErrorMessage(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenSubjectNotExists() {
        // given
        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto("Przyroda", null, SchoolClassId.wrap(1L));
        when(repository.existsByNameAndSchoolClassId("Przyroda", 1L)).thenReturn(false);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }
}
