package pl.edziennik.web.command.director;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.director.create.CreateDirectorCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Director API")
public class DirectorCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new director account",
            description = "This API endpoint creates a new director account that will be assigned to a given schoolId, " +
                    "keep in mind that only one director can exist for every school")
    @PostMapping("/schools/{schoolId}/directors")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createDirector(@PathVariable @NotNull(message = "${school.empty}") SchoolId schoolId,
                                               @RequestBody CreateDirectorCommand requestCommand) {
        CreateDirectorCommand command = new CreateDirectorCommand(schoolId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.directorId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
