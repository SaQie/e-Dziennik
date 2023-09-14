package pl.edziennik.web.command.subject;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.subject.create.CreateSubjectCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SubjectCommandController {

    private final Dispatcher dispatcher;


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

}
