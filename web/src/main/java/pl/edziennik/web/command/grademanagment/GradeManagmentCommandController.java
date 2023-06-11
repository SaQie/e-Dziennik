package pl.edziennik.web.command.grademanagment;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.grademanagment.assigngrade.AssignGradeToStudentSubjectCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/grade-managment")
public class GradeManagmentCommandController {

    private final Dispatcher dispatcher;

    @PostMapping
    @Operation(summary = "Add a grade to student subject")
    public ResponseEntity<Void> addStudentSubjectGrade(@RequestBody @Valid AssignGradeToStudentSubjectCommand command) {
        dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromPath("/api/v1/students/{studentId}/subjects/{subjectId}/grades")
                .buildAndExpand(command.studentId().id(), command.studentId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
