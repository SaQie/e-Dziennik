package pl.edziennik.web.query.classroomschedule;

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
public class ClassRoomScheduleQueryController {

    private final ClassRoomScheduleQueryDao dao;

    @GetMapping("/classrooms/{classRoomId}/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomScheduleSummaryView> getClassRoomSchedule(Pageable pageable, @PathVariable ClassRoomId classRoomId) {
        return dao.getClassRoomScheduleSummaryView(pageable, classRoomId);
    }

    @GetMapping("/schools/{schoolId}/classrooms/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomScheduleSummaryForSchoolView> getClassRoomsSchedule(Pageable pageable, @PathVariable SchoolId schoolId) {
        return dao.getClassRoomsSchedulesSummaryForSchoolView(pageable, schoolId);
    }
}
