package pl.edziennik.web.command.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Student API")
public class StudentCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new student account",
            description = "This API endpoint creates a new student account that will be assigned to the given schoolClassId. " +
                    "Keep in mind if school-class configuration has parameter 'AutoAssignSubject' set to true, all subjects " +
                    "assigned to this school class will be also assigned to student which is created")
    @PostMapping("/school-classes/{schoolClassId}/students")
    public ResponseEntity<Void> createStudent(@PathVariable @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId,
                                              @RequestBody @Valid CreateStudentCommand requestCommand) {
        CreateStudentCommand command = new CreateStudentCommand(schoolClassId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.studentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete student account with the given identifier")
    @DeleteMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStudent(@PathVariable StudentId studentId) {
        dispatcher.run(new DeleteStudentCommand(studentId));

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign parent to student",
            description = "This API endpoint assigns student to parent.")
    @PostMapping("/students/{studentId}/parents/{parentId}/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> assignParent(@PathVariable StudentId studentId, @PathVariable ParentId parentId) {
        AssignParentCommand command = new AssignParentCommand(studentId, parentId);

        dispatcher.run(command);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change student address data")
    @PutMapping("/students/{studentId}/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable StudentId studentId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(studentId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.STUDENT);

        dispatcher.run(command);
    }

}
