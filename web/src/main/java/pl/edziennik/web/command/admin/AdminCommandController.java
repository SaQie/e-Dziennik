package pl.edziennik.web.command.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.admin.CreateAdminCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.AdminId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add administration account", description = "Add administration account, that will have all " +
            "permisions to manage application")
    public ResponseEntity<AdminId> createAdmin(@RequestBody CreateAdminCommand command) {
        OperationResult operationResult = dispatcher.callHandler(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
