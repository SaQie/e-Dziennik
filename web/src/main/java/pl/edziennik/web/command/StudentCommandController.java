package pl.edziennik.web.command;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.student.service.IStudentCommandService;
import pl.edziennik.application.command.student.service.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.domain.student.StudentId;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students/")
@AllArgsConstructor
public class StudentCommandController {

    private final IStudentCommandService commandService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new student")
    public ResponseEntity<StudentId> createStudent(@RequestBody CreateStudentCommand command) {
        OperationResult result = commandService.createUser(command);
        return ResponseEntity.of(Optional.empty());
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequestUri()
//                .path("/{id}")
//                .buildAndExpand(studentId.id())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
    }

}
