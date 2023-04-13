package pl.edziennik.eDziennik.domain.teacher.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private TeacherPeselNotUniqueValidator validator;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenTeacherWithPeselAlreadyExists() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        when(teacherRepository.isTeacherExistsByPesel(Pesel.of("123123"), Role.RoleConst.ROLE_TEACHER.getId())).thenReturn(true);
        lenient().when(resourceCreator.of(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.pesel()))
                .thenReturn(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), TeacherPeselNotUniqueValidator.TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME);
        assertEquals(error.getField(), TeacherRequestApiDto.PESEL);
        assertEquals(error.getCause(), getErrorMessage(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE));
    }

    @Test
    public void shouldNotReturnApiErrorWhenTeacherWithPeselNotExists(){
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        when(teacherRepository.isTeacherExistsByPesel(Pesel.of("123123"), Role.RoleConst.ROLE_TEACHER.getId())).thenReturn(false);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }


}
