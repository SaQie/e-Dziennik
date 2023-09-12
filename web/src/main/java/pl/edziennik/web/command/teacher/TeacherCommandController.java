package pl.edziennik.web.command.teacher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.address.changeaddress.ChangeAddressCommand;
import pl.edziennik.application.command.teacher.create.CreateTeacherCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TeacherCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/schools/{schoolId}/teachers")
    public ResponseEntity<Void> createTeacher(@PathVariable @NotNull(message = "{school.empty}") SchoolId schoolId,
                                              @RequestBody @Valid CreateTeacherCommand requestCommand) {
        CreateTeacherCommand command = new CreateTeacherCommand(schoolId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.teacherId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/teachers/{teacherId}/address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable TeacherId teacherId, @RequestBody ChangeAddressCommand command) {
        command = new ChangeAddressCommand(teacherId.id(),
                command.address(),
                command.city(),
                command.postalCode(),
                ChangeAddressCommand.CommandFor.TEACHER);

        dispatcher.run(command);
    }

}
