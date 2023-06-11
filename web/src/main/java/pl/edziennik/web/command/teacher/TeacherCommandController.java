package pl.edziennik.web.command.teacher;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.teacher.create.CreateTeacherCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherCommandController {

    private final Dispatcher dispatcher;

    @PostMapping
    @Operation(summary = "Add teacher account")
    public ResponseEntity<Void> createTeacher(@RequestBody @Valid CreateTeacherCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{teacherId}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable TeacherId teacherId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(teacherId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.TEACHER);

        dispatcher.dispatch(command);
    }

}
