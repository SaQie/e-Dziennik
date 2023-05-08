package pl.edziennik.web.command;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.student.assignparent.AssignParentCommand;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.command.student.delete.DeleteStudentCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.domain.parent.ParentId;
import pl.edziennik.domain.student.StudentId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/students/")
@AllArgsConstructor
public class StudentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new student")
    public ResponseEntity<Void> createStudent(@RequestBody CreateStudentCommand command) {
        OperationResult operationResult = dispatcher.callHandler(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete specific student")
    public ResponseEntity<Void> deleteStudent(@PathVariable StudentId studentId) {
        dispatcher.callHandler(new DeleteStudentCommand(studentId));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{studentId}/parents/{parentId}/assign")
    public ResponseEntity<Void> assignParent(@PathVariable StudentId studentId, @PathVariable ParentId parentId) {
        dispatcher.callHandler(new AssignParentCommand(studentId, parentId));

        return ResponseEntity.noContent().build();
    }

}
