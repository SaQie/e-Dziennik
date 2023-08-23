package pl.edziennik.web.query.teacherschedule;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TeacherScheduleQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/teachers/{teacherId}/schedules")
    @ResponseStatus(HttpStatus.OK)
    public void getTeacherSchedule(Pageable pageable, @PathVariable TeacherId teacherId) {

    }

    @GetMapping("/schools/{schoolId}/teachers/schedules")
    @ResponseStatus(HttpStatus.OK)
    public void getTeachersScheduleForSchool(Pageable pageable, @PathVariable SchoolId schoolId) {

    }

}
