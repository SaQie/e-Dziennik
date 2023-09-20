package pl.edziennik.web.command.classroom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.classroom.changename.ChangeClassRoomNameCommand;
import pl.edziennik.application.command.classroom.create.CreateClassRoomCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Class-room API")
public class ClassRoomCommandController {

    private final Dispatcher dispatcher;


    @Operation(summary = "Create a new class room",
            description = "This API endpoint creates a new class-room that will be assigned to given schoolId")
    @PostMapping("/schools/{schoolId}/class-rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createClassRoom(@PathVariable SchoolId schoolId, @RequestBody @Valid CreateClassRoomCommand requestCommand) {
        CreateClassRoomCommand command = new CreateClassRoomCommand(schoolId, requestCommand.classRoomName());

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.classRoomId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Change class room name",
            description = "This API endpoint changes the class room name")
    @PatchMapping("/{classRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> changeClassRoomName(@PathVariable ClassRoomId classRoomId, @RequestBody @Valid ChangeClassRoomNameCommand request) {
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(classRoomId, request.classRoomName());
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(classRoomId.id())
                .toUri();

        return ResponseEntity.noContent().location(location).build();
    }

}
