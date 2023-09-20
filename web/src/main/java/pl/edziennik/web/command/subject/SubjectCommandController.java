package pl.edziennik.web.command.subject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.subject.create.CreateSubjectCommand;
import pl.edziennik.application.command.subject.delete.DeleteSubjectCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SubjectId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Subject API")
public class SubjectCommandController {

    private final Dispatcher dispatcher;


    @Operation(summary = "Create a new subject",
            description = "This API endpoint creates a new subject that will be assigned to the given schoolClassId")
    @PostMapping("/school-classes/{schoolClassId}/subjects")
    public ResponseEntity<Void> createSubject(@PathVariable @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId,
                                              @RequestBody @Valid CreateSubjectCommand requestCommand) {
        CreateSubjectCommand command = new CreateSubjectCommand(schoolClassId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.subjectId().id())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @Operation(summary = "Delete subject with the given identifier")
    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable SubjectId subjectId) {
        DeleteSubjectCommand command = new DeleteSubjectCommand(subjectId);

        dispatcher.run(command);

        return ResponseEntity.noContent().build();
    }

}
