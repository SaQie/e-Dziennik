package pl.edziennik.web.command.teacherschedule;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.teacherschedule.create.CreateTeacherScheduleCommand;
import pl.edziennik.application.common.dispatcher.newapi.Dispatcher2;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TeacherScheduleCommandController {

    private final Dispatcher2 dispatcher;

    @PostMapping("/teachers/{teacherId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTeacherSchedule(@PathVariable TeacherId teacherId, @RequestBody @Valid CreateTeacherScheduleCommand requestCommand) {
        CreateTeacherScheduleCommand command = new CreateTeacherScheduleCommand(teacherId, requestCommand);

        // TODO dorob uri jak zrobisz query

        dispatcher.run(command);
    }

}
