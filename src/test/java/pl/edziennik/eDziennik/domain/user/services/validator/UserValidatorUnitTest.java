package pl.edziennik.eDziennik.domain.user.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private UserWithEmailAlreadyExistsValidator emailValidator;

    @InjectMocks
    private UserWithUsernameAlreadyExistsValidator usernameValidator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenUserWithEmailAlreadyExists() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        lenient().when(resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.getEmail()))
                .thenReturn(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL);

        // when
        Optional<ApiValidationResult> validationResult = emailValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), UserValidators.USER_WITH_EMAIL_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), UserRequestDto.EMAIL);
        assertEquals(error.getCause(), getErrorMessage(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL));
    }

    @Test
    public void shouldReturnApiErrorWhenUserWithUsernameAlreadyExists() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");
        when(userRepository.existsByUsername("Kamil")).thenReturn(true);
        lenient().when(resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.getUsername()))
                .thenReturn(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS);

        // when
        Optional<ApiValidationResult> validationResult = usernameValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), UserValidators.USER_WITH_USERNAME_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), UserRequestDto.USERNAME);
        assertEquals(error.getCause(), getErrorMessage(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS));
    }

    @Test
    public void shouldNotReturnApiErrorWhenUserWithUsernameNotExists() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");
        when(userRepository.existsByUsername("Kamil")).thenReturn(false);

        // when
        Optional<ApiValidationResult> validationResult = usernameValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenUserWithEmailNotExists() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // when
        Optional<ApiValidationResult> validationResult = emailValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }
}
