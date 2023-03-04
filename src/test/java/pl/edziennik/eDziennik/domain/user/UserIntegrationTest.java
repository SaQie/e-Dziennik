package pl.edziennik.eDziennik.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.domain.user.services.validator.UserValidators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class UserIntegrationTest extends BaseTest {

    @Test
    public void shouldSaveUser() {
        // given
        UserRequestDto dto = userUtil.prepareRequestDto("Kamil", "test@example.com");

        // when
        User user = userService.createUser(dto);

        // then
        User actual = find(User.class, user.getId());
        assertEquals(actual.getUsername(), dto.getUsername());
        assertEquals(actual.getRole().getName(), dto.getRole());
        assertEquals(actual.getEmail(), dto.getEmail());
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
        String expectedExceptionMessage = resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.getUsername());
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
        String expectedExceptionMessage = resourceCreator.of(UserValidators.EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.getEmail());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
