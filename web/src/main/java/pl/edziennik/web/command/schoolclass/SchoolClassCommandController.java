package pl.edziennik.web.command.schoolclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.schoolclass.changeconfig.ChangeSchoolClassConfigurationValuesCommand;
import pl.edziennik.application.command.schoolclass.create.CreateSchoolClassCommand;
import pl.edziennik.application.command.schoolclass.delete.DeleteSchoolClassCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "School-class API")
public class SchoolClassCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new school-class",
            description = "This API endpoint creates a new school-class that will be assigned to the given schoolId")
    @PostMapping("/schools/{schoolId}/school-classes")
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


    @Operation(summary = "Change school-class configuration data")
    @PatchMapping("/school-classes/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSchoolClassConfigurationValues(@RequestBody @Valid ChangeSchoolClassConfigurationValuesCommand commandBody,
                                                     @PathVariable SchoolClassId schoolClassId) {
        ChangeSchoolClassConfigurationValuesCommand command = new ChangeSchoolClassConfigurationValuesCommand(schoolClassId, commandBody.maxStudentsSize(), commandBody.autoAssignSubjects());

        dispatcher.run(command);
    }

    @Operation(summary = "Delete school-class with given identifier")
    @DeleteMapping("/school-classes/{schoolClassId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchoolClass(@PathVariable SchoolClassId schoolClassId) {
        DeleteSchoolClassCommand command = new DeleteSchoolClassCommand(schoolClassId);

        dispatcher.run(command);
    }

}
