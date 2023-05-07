package pl.edziennik.eDziennik.command.student;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.command.student.service.create.CreateStudentCommand;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/students/")
@AllArgsConstructor
public class StudentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new student")
    public ResponseEntity<StudentId> createStudent(@RequestBody CreateStudentCommand command){
        StudentId studentId = dispatcher.callHandler(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(studentId.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
