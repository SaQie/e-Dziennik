package pl.edziennik.web.command.subjectmanagment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.subjectmanagment.assign.AssignSubjectToStudentCommand;
import pl.edziennik.application.command.subjectmanagment.unassign.UnassignStudentFromSubjectCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subject-management")
@Tag(name = "Subject API")
public class SubjectManagmentCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Assign subject to student",
            description = "This API endpoint manually assigns a subject to the given student")
    @PostMapping("/assign")
    public ResponseEntity<Void> assignSubjectToStudent(@RequestBody @Valid AssignSubjectToStudentCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/students/{studentId}/subjects/{subjectId}/grades")
                .buildAndExpand(command.studentId().id(), command.studentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Unassign subject from student",
            description = "This API endpoint unassigns a subject from student")
    @PostMapping("/unassign")
    public ResponseEntity<Void> unassignSubjectFromStudent(@RequestBody @Valid UnassignStudentFromSubjectCommand command) {
        dispatcher.run(command);

        return ResponseEntity.noContent().build();
    }

}
