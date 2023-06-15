package pl.edziennik.web.command.schoolclass;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.schoolclass.changeconfig.ChangeSchoolClassConfigurationValuesCommand;
import pl.edziennik.application.command.schoolclass.create.CreateSchoolClassCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.SchoolClassId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/schoolclasses")
@AllArgsConstructor
public class SchoolClassCommandController {

    private final Dispatcher dispatcher;

    @PostMapping
    public ResponseEntity<Void> createSchoolClass(@RequestBody @Valid CreateSchoolClassCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();

    }


    @PatchMapping("/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSchoolClassConfigurationValues(@RequestBody @Valid ChangeSchoolClassConfigurationValuesCommand commandBody, @PathVariable SchoolClassId schoolClassId) {
        ChangeSchoolClassConfigurationValuesCommand command = new ChangeSchoolClassConfigurationValuesCommand(schoolClassId, commandBody.maxStudentsSize(), commandBody.autoAssignSubjects());

        dispatcher.dispatch(command);
    }

}
