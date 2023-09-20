package pl.edziennik.web.command.lessonplan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Lesson-plan API")
public class LessonPlanCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new lesson plan",
            description = "This API endpoint creates a new lesson plan that will be assigned to the given schoolClassId." +
                    "Keep in mind that always if you create a new lesson-plan, it will also created a new teacher-schedule and class-room schedule for " +
                    "the given time-frame.")
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
