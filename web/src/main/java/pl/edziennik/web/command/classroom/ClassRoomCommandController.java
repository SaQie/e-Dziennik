package pl.edziennik.web.command.classroom;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.classroom.changename.ChangeClassRoomNameCommand;
import pl.edziennik.application.command.classroom.create.CreateClassRoomCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClassRoomCommandController {

    private final Dispatcher dispatcher;


    @PostMapping("/schools/{schoolId}/classrooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createClassRoom(@PathVariable SchoolId schoolId, @RequestBody @Valid CreateClassRoomCommand requestCommand) {
        CreateClassRoomCommand command = new CreateClassRoomCommand(schoolId, requestCommand);
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{classRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> changeClassRoomName(@PathVariable ClassRoomId classRoomId, @RequestBody @Valid ChangeClassRoomNameCommand request) {
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(classRoomId, request.classRoomName());
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.noContent().location(location).build();
    }

}