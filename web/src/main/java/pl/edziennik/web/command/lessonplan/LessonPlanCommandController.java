package pl.edziennik.web.command.lessonplan;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LessonPlanCommandController {

    @PostMapping("/schoolclasses/{schoolClassId}/lessonplans")
    public void test(){

    }
}
