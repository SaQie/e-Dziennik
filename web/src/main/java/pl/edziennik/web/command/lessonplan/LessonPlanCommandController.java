package pl.edziennik.web.command.lessonplan;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LessonPlanCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/schoolclasses/{schoolClassId}/lessonplans")
    public void createLessonPlan(@PathVariable SchoolClassId schoolClassId, @RequestBody @Valid CreateLessonPlanCommand requestCommand) {
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(schoolClassId, requestCommand);

        // TODO dorob uri jak zrobisz query

        dispatcher.run(command);


    }
}
