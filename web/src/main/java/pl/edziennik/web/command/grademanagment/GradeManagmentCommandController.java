package pl.edziennik.web.command.grademanagment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.grademanagment.assign.AssignGradeToStudentSubjectCommand;
import pl.edziennik.application.command.grademanagment.unassign.UnassignGradeFromStudentSubjectCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/grade-managment")
@Tag(name = "Grade API")
public class GradeManagmentCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Assign a new grade to the student's subject",
            description = "This API endpoint creates a new grade that will be assigned to the given in body student's subject")
    @PostMapping("/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> assignStudentSubjectGrade(@RequestBody @Valid AssignGradeToStudentSubjectCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/students/{studentId}/subjects/{subjectId}/grades")
                .buildAndExpand(command.studentId().id(), command.studentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Unassign existing grade from the student's subject",
            description = "This API endpoint unassigns existing grade from assigned to this grade student's subject")
    @PostMapping("/unassign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> unassignStudentSubjectGrade(@RequestBody @Valid UnassignGradeFromStudentSubjectCommand command) {
        dispatcher.run(command);

        return ResponseEntity.noContent().build();
    }

}
