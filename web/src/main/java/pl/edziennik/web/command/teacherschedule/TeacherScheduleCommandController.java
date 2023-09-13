package pl.edziennik.web.command.teacherschedule;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.teacherschedule.create.CreateTeacherScheduleCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TeacherScheduleCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/teachers/{teacherId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTeacherSchedule(@PathVariable TeacherId teacherId, @RequestBody @Valid CreateTeacherScheduleCommand requestCommand) {
        CreateTeacherScheduleCommand command = new CreateTeacherScheduleCommand(teacherId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/teachers/{teacherId}/schedules")
                .buildAndExpand(command.teacherId().id())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
