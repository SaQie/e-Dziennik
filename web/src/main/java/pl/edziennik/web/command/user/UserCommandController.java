package pl.edziennik.web.command.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.user.activate.ActivateUserCommand;
import pl.edziennik.application.command.user.changepassword.ChangePasswordCommand;
import pl.edziennik.application.command.user.changeuserdata.ChangeUserDataCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.valueobject.vo.Token;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User API")
public class UserCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Activate user account",
            description = "This API endpoint activates a user account that has been created before")
    @PatchMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateUser(@RequestParam(name = "token") String token) {
        ActivateUserCommand command = new ActivateUserCommand(Token.of(token));

        dispatcher.run(command);
    }

    @Operation(summary = "Change user data",
            description = "This API endpoint allows you to change email/username of user")
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserData(@PathVariable UserId userId, @RequestBody @Valid ChangeUserDataCommand command) {
        command = new ChangeUserDataCommand(userId, command.username(), command.email());

        dispatcher.run(command);
    }

    @Operation(summary = "Change user password")
    @PatchMapping("/{userId}/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable UserId userId, @RequestBody @Valid ChangePasswordCommand command) {
        command = new ChangePasswordCommand(userId, command.oldPassword(), command.newPassword());

        dispatcher.run(command);
    }

}
