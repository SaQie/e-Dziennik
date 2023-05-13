package pl.edziennik.web.command.school;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.school.create.CreateSchoolCommand;
import pl.edziennik.application.command.school.delete.DeleteSchoolCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/schools/")
@AllArgsConstructor
public class SchoolCommandController {

    private final Dispatcher dispatcher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new school")
    public ResponseEntity<Void> createSchool(@RequestBody @Valid CreateSchoolCommand createSchoolCommand) {
        OperationResult operationResult = dispatcher.callHandler(createSchoolCommand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete specific school")
    public ResponseEntity<Void> deleteSchool(@PathVariable SchoolId schoolId) {
        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(schoolId);

        dispatcher.callHandler(deleteSchoolCommand);

        return ResponseEntity.ok().build();
    }


}
