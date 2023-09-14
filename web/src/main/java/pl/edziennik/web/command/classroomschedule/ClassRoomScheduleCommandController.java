package pl.edziennik.web.command.classroomschedule;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.classroomschedule.create.CreateClassRoomScheduleCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.ClassRoomId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClassRoomScheduleCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/class-rooms/{classRoomId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createClassRoomSchedule(@PathVariable ClassRoomId classRoomId, @RequestBody @Valid CreateClassRoomScheduleCommand requestCommand) {
        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoomId, requestCommand);

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/class-rooms/{classRoomId}/schedules")
                .buildAndExpand(command.classRoomScheduleId().id())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
