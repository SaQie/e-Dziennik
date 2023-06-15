package pl.edziennik.web.command.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.admin.create.CreateAdminCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    public ResponseEntity<Void> createAdmin(@RequestBody @Valid CreateAdminCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
