package pl.edziennik.eDziennik.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.services.validator.UserValidators;
import pl.edziennik.eDziennik.server.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class UserIntegrationTest extends BaseTesting {

    @Test
    public void shouldSaveUser() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");

        // when
        User user = userService.createUser(dto);

        // then
        User actual = find(User.class, user.getUserId());
        assertEquals(actual.getUsername(), dto.username());
        assertEquals(actual.getRole().getName(), dto.role());
        assertEquals(actual.getEmail(), dto.email());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveExistingUser() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");

        userService.createUser(dto);
        UserRequestDto dto2 = userUtil.prepareRequestDto("Kamil", "test1@example.com");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.createUser(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(UserValidators.USER_WITH_USERNAME_ALREADY_EXIST_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(UserRequestDto.USERNAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.username());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveUserWithTheSameEmail() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");

        userService.createUser(dto);
        UserRequestDto dto2 = userUtil.prepareRequestDto("Kamil1", "test@example.com");
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.createUser(dto2));


        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(UserValidators.USER_WITH_EMAIL_ALREADY_EXIST_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(UserRequestDto.EMAIL, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.email());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
