package pl.edziennik.web.query.classroomschedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.classroomschedule.ClassRoomScheduleQueryDao;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Class-room API")
public class ClassRoomScheduleQueryController {

    private final ClassRoomScheduleQueryDao dao;

    @Operation(summary = "Get given class-room schedules",
            description = "This API endpoint returns a paginated class-room schedules data")
    @GetMapping("/class-rooms/{classRoomId}/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomScheduleSummaryView> getClassRoomSchedule(Pageable pageable, @PathVariable ClassRoomId classRoomId) {
        return dao.getClassRoomScheduleSummaryView(pageable, classRoomId);
    }

    @Operation(summary = "Get detailed information about the given class-room schedule")
    @GetMapping("/schools/{schoolId}/class-rooms/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomScheduleSummaryForSchoolView> getClassRoomsSchedule(Pageable pageable, @PathVariable SchoolId schoolId) {
        return dao.getClassRoomsSchedulesSummaryForSchoolView(pageable, schoolId);
    }
}
