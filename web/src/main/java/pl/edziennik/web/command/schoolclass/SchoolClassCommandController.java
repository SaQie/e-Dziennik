package pl.edziennik.web.command.schoolclass;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.schoolclass.changeconfig.ChangeSchoolClassConfigurationValuesCommand;
import pl.edziennik.application.command.schoolclass.create.CreateSchoolClassCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SchoolClassCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/schools/{schoolId}/schoolclasses")
    public ResponseEntity<Void> createSchoolClass(@PathVariable @NotNull(message = "${school.empty}") SchoolId schoolId,
                                                  @RequestBody @Valid CreateSchoolClassCommand requestCommand) {
        CreateSchoolClassCommand command = new CreateSchoolClassCommand(schoolId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.schoolClassId().id())
                .toUri();

        return ResponseEntity.created(location).build();

    }


    @PatchMapping("/schoolclasses/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSchoolClassConfigurationValues(@RequestBody @Valid ChangeSchoolClassConfigurationValuesCommand commandBody,
                                                     @PathVariable SchoolClassId schoolClassId) {
        ChangeSchoolClassConfigurationValuesCommand command = new ChangeSchoolClassConfigurationValuesCommand(schoolClassId, commandBody.maxStudentsSize(), commandBody.autoAssignSubjects());

        dispatcher.run(command);
    }

}
