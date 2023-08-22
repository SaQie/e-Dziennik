package pl.edziennik.web.command.school;

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
    public ResponseEntity<Void> createSchool(@RequestBody @Valid CreateSchoolCommand createSchoolCommand) {
        OperationResult operationResult = dispatcher.dispatch(createSchoolCommand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("{schoolId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable SchoolId schoolId) {
        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(schoolId);

        dispatcher.dispatch(deleteSchoolCommand);
    }

    @PutMapping("/{schoolId}/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable SchoolId schoolId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(schoolId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.SCHOOL);

        dispatcher.dispatch(command);
    }

    @PatchMapping("/{schoolId}/configurations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSchoolConfigurationValues(@RequestBody @Valid ChangeSchoolConfigurationValuesCommand command, @PathVariable SchoolId schoolId) {
        command = new ChangeSchoolConfigurationValuesCommand(schoolId, command.averageType(), command.maxLessonTime());

        dispatcher.dispatch(command);
    }


}
