package pl.edziennik.web.command.teacherschedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.teacherschedule.create.CreateTeacherScheduleCommand;
import pl.edziennik.application.command.teacherschedule.delete.DeleteTeacherScheduleCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Teacher API")
public class TeacherScheduleCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new teacher schedule",
            description = "This API creates a new teacher schedule a with specific time frame (from time -> to time)")
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

    @Operation(summary = "Delete existing teacher schedule")
    @DeleteMapping("/teacher-schedules/{teacherScheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeacherSchedule(@PathVariable TeacherScheduleId teacherScheduleId) {
        DeleteTeacherScheduleCommand command = new DeleteTeacherScheduleCommand(teacherScheduleId);

        dispatcher.run(command);
    }

}
