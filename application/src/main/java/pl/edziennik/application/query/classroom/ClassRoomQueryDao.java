package pl.edziennik.application.query.classroom;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;

public interface ClassRoomQueryDao {


    PageView<ClassRoomForSchoolView> getClassRoomSummaryForSchoolView(Pageable pageable, SchoolId schoolId);

}
