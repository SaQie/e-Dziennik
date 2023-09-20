package pl.edziennik.web.command.classroomschedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Class-room API")
public class ClassRoomScheduleCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new class-room schedule",
            description = "This API endpoint creates a new class room schedule, keep in mind that, always if you create lesson-plan for given class-room " +
                    "the class-room schedule will be created in background automatically")
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
