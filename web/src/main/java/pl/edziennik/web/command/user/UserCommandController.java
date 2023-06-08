package pl.edziennik.web.command.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.user.activate.ActivateUserCommand;
import pl.edziennik.application.command.user.changepassword.ChangePasswordCommand;
import pl.edziennik.application.command.user.changeuserdata.ChangeUserDataCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.Token;
import pl.edziennik.common.valueobject.id.UserId;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserCommandController {

    private final Dispatcher dispatcher;

    @GetMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateUser(@RequestParam(name = "token") String token) {
        ActivateUserCommand command = new ActivateUserCommand(Token.of(token));

        dispatcher.dispatch(command);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserData(@PathVariable UserId userId, @RequestBody @Valid ChangeUserDataCommand command) {
        command = new ChangeUserDataCommand(userId, command.username(), command.email());

        dispatcher.dispatch(command);
    }

    @PatchMapping("/{userId}/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable UserId userId, @RequestBody @Valid ChangePasswordCommand command) {
        command = new ChangePasswordCommand(userId, command.oldPassword(), command.newPassword());

        dispatcher.dispatch(command);
    }

}
