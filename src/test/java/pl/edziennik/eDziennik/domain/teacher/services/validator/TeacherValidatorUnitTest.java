package pl.edziennik.eDziennik.domain.teacher.services.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.teacher.TeacherIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TeacherValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private TeacherPeselNotUniqueValidator validator;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenTeacherWithPeselAlreadyExists() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        when(teacherDao.isTeacherExistsByPesel("123123")).thenReturn(true);
        lenient().when(resourceCreator.of(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel()))
                .thenReturn(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), TeacherPeselNotUniqueValidator.TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME);
        assertEquals(error.getField(), TeacherRequestApiDto.PESEL);
        assertEquals(error.getCause(), getErrorMessage(TeacherPeselNotUniqueValidator.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE));
    }

    @Test
    public void shouldNotReturnApiErrorWhenTeacherWithPeselNotExists(){
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        when(teacherDao.isTeacherExistsByPesel("123123")).thenReturn(false);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }


}
