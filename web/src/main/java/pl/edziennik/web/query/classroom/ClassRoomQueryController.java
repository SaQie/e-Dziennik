package pl.edziennik.web.query.classroom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.classroom.ClassRoomQueryDao;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Class-room API")
public class ClassRoomQueryController {

    private final ClassRoomQueryDao dao;

    @Operation(summary = "Get class-rooms assigned to given school",
            description = "This API endpoint returns a paginated list of class-rooms that are assigned to the given schoolId")
    @GetMapping("/schools/{schoolId}/class-rooms")
    @ResponseStatus(HttpStatus.OK)
    public PageView<ClassRoomForSchoolView> getClassRoomsForSchool(Pageable pageable, @PathVariable SchoolId schoolId) {
        return dao.getClassRoomSummaryForSchoolView(pageable, schoolId);
    }
}
