package pl.edziennik.web.command.subjectmanagment;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.subjectmanagment.assigntostudent.AssignSubjectToStudentCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subject-managment")
public class SubjectManagmentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping
    @Operation(summary = "Assign a subject to student")
    public ResponseEntity<Void> assignSubjectToStudent(@RequestBody @Valid AssignSubjectToStudentCommand command) {
        dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromPath("/api/v1/students/{studentId}/subjects/{subjectId}/grades")
                .buildAndExpand(command.studentId().id(), command.studentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
