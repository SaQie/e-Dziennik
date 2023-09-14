package pl.edziennik.web.command.lessonplan;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolClassId;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LessonPlanCommandController {

    private final Dispatcher dispatcher;

    @PostMapping("/school-classes/{schoolClassId}/lesson-plans")
    public ResponseEntity<Void> createLessonPlan(@PathVariable SchoolClassId schoolClassId, @RequestBody @Valid CreateLessonPlanCommand requestCommand) {
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(schoolClassId, requestCommand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/school-classes/lesson-plans/{lessonPlanId}")
                .buildAndExpand(command.lessonPlanId().id())
                .toUri();

        dispatcher.run(command);

        return ResponseEntity.created(location).build();
    }
}
