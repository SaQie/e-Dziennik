package pl.edziennik.web.command.student;

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
public class StudentCommandController {

    private final Dispatcher dispatcher;

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

    @DeleteMapping("/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStudent(@PathVariable StudentId studentId) {
        dispatcher.run(new DeleteStudentCommand(studentId));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/students/{studentId}/parents/{parentId}/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> assignParent(@PathVariable StudentId studentId, @PathVariable ParentId parentId) {
        AssignParentCommand command = new AssignParentCommand(studentId, parentId);

        dispatcher.run(command);

        return ResponseEntity.noContent().build();
    }

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
