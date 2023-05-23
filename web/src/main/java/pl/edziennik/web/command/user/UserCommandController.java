package pl.edziennik.web.command.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.user.activate.ActivateUserCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.Token;

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

}
