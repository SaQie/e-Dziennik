package pl.edziennik.web.command.parent;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.parent.create.CreateParentCommand;
import pl.edziennik.application.common.dispatcher.newapi.Dispatcher2;
import pl.edziennik.common.valueobject.id.ParentId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parents")
@AllArgsConstructor
public class ParentCommandController {

    private final Dispatcher2 dispatcher;

    @PostMapping()
    public ResponseEntity<Void> createParent(@RequestBody @Valid CreateParentCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.parentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{parentId}/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable ParentId parentId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(parentId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.PARENT);

        dispatcher.run(command);
    }

}
