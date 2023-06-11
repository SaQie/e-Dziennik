package pl.edziennik.web.command.school;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
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
    @Operation(summary = "Create new school")
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
    @Operation(summary = "Delete specific school")
    public void deleteSchool(@PathVariable SchoolId schoolId) {
        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(schoolId);

        dispatcher.dispatch(deleteSchoolCommand);
    }

    @PutMapping("/{schoolId}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable SchoolId schoolId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(schoolId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.SCHOOL);

        dispatcher.dispatch(command);
    }


}
