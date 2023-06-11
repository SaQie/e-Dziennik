package pl.edziennik.web.command.parent;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.parent.create.CreateParentCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.ParentId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parents")
@AllArgsConstructor
public class ParentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @Operation(summary = "Add a new parent")
    public ResponseEntity<Void> createParent(@RequestBody @Valid CreateParentCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{parentId}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable ParentId parentId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(parentId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.PARENT);

        dispatcher.dispatch(command);
    }

}
