package pl.edziennik.web.command.classroomschedule;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.classroomschedule.create.CreateClassRoomScheduleCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.ClassRoomId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClassRoomScheduleCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/classrooms/{classRoomId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClassRoomSchedule(@PathVariable ClassRoomId classRoomId, @RequestBody @Valid CreateClassRoomScheduleCommand requestCommand) {
        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoomId, requestCommand);

        // TODO dorob uri jak zrobisz query

        dispatcher.run(command);
    }

}
