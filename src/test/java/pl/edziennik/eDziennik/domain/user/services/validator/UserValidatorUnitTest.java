package pl.edziennik.eDziennik.domain.user.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.user.UserIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.user.dao.UserDao;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorUnitTest extends BaseUnitTest {

    private UserIntegrationTestUtil util;

    public UserValidatorUnitTest() {
        this.util = new UserIntegrationTestUtil();
    }

    @InjectMocks
    private UserWithEmailAlreadyExistsValidator emailValidator;

    @InjectMocks
    private UserWithUsernameAlreadyExistsValidator usernameValidator;

    @Mock
    private UserDao dao;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenUserWithEmailAlreadyExists() {
        // given
        UserRequestDto dto = util.prepareRequestDto("Kamil", "test@example.com");
        when(dao.isUserExistByEmail("test@example.com")).thenReturn(true);
        lenient().when(resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.getEmail()))
                .thenReturn(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL);

        // when
        Optional<ApiErrorDto> validationResult = emailValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), UserValidators.USER_WITH_EMAIL_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), UserRequestDto.EMAIL);
        assertEquals(error.getCause(), getErrorMessage(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL));
    }

    @Test
    public void shouldReturnApiErrorWhenUserWithUsernameAlreadyExists() {
        // given
        UserRequestDto dto = util.prepareRequestDto("Kamil", "test@example.com");
        when(dao.isUserExistByUsername("Kamil")).thenReturn(true);
        lenient().when(resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.getUsername()))
                .thenReturn(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS);

        // when
        Optional<ApiErrorDto> validationResult = usernameValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), UserValidators.USER_WITH_USERNAME_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), UserRequestDto.USERNAME);
        assertEquals(error.getCause(), getErrorMessage(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS));
    }

    @Test
    public void shouldNotReturnApiErrorWhenUserWithUsernameNotExists(){
        // given
        UserRequestDto dto = util.prepareRequestDto("Kamil", "test@example.com");
        when(dao.isUserExistByUsername("Kamil")).thenReturn(false);

        // when
        Optional<ApiErrorDto> validationResult = usernameValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenUserWithEmailNotExists(){
        // given
        UserRequestDto dto = util.prepareRequestDto("Kamil", "test@example.com");
        when(dao.isUserExistByEmail("test@example.com")).thenReturn(false);

        // when
        Optional<ApiErrorDto> validationResult = emailValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }
}
