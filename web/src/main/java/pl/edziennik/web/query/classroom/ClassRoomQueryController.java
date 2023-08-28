package pl.edziennik.web.query.classroom;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.classroom.GetClassRoomForSchoolQuery;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClassRoomQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/schools/{schoolId}/classrooms")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomForSchoolView> getClassRoomsForSchool(Pageable pageable, @PathVariable SchoolId schoolId) {
        GetClassRoomForSchoolQuery query = new GetClassRoomForSchoolQuery(schoolId, pageable);

        return dispatcher.dispatch(query);
    }
}
