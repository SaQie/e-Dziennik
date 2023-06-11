package pl.edziennik.web.command.student;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.student.assignparent.AssignParentCommand;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.command.student.delete.DeleteStudentCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @Operation(summary = "Add new student")
    public ResponseEntity<Void> createStudent(@RequestBody @Valid CreateStudentCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete specific student")
    public ResponseEntity<Void> deleteStudent(@PathVariable StudentId studentId) {
        dispatcher.dispatch(new DeleteStudentCommand(studentId));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/parents/{parentId}/assign")
    public ResponseEntity<Void> assignParent(@PathVariable StudentId studentId, @PathVariable ParentId parentId) {
        dispatcher.dispatch(new AssignParentCommand(studentId, parentId));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{studentId}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable StudentId studentId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(studentId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.STUDENT);

        dispatcher.dispatch(command);
    }

}
