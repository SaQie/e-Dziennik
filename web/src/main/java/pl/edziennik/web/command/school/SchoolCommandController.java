package pl.edziennik.web.command.school;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.school.changeconfig.ChangeSchoolConfigurationValuesCommand;
import pl.edziennik.application.command.school.create.CreateSchoolCommand;
import pl.edziennik.application.command.school.delete.DeleteSchoolCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/schools/")
@AllArgsConstructor
@Tag(name = "School API")
public class SchoolCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new school",
            description = "This API endpoint creates a new school. If the school is successfully created, in background will also created " +
                    "a school configuration with default values")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createSchool(@RequestBody @Valid CreateSchoolCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.schoolId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Delete school with given identifier")
    @DeleteMapping("{schoolId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable SchoolId schoolId) {
        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(schoolId);

        dispatcher.run(deleteSchoolCommand);
    }

    @Operation(summary = "Change school address data")
    @PutMapping("/{schoolId}/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable SchoolId schoolId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(schoolId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.SCHOOL);

        dispatcher.run(command);
    }

    @Operation(summary = "Change school configuration data")
    @PatchMapping("/{schoolId}/configurations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSchoolConfigurationValues(@RequestBody @Valid ChangeSchoolConfigurationValuesCommand command,
                                                @PathVariable SchoolId schoolId) {
        command = new ChangeSchoolConfigurationValuesCommand(schoolId, command.averageType(), command.maxLessonTime(), command.minScheduleTime());

        dispatcher.run(command);
    }


}
