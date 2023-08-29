package pl.edziennik.application.query.classroomschedule;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleQueryRepository;

@Repository
@AllArgsConstructor
class ClassRoomScheduleQueryDaoImpl implements ClassRoomScheduleQueryDao {

    private final ClassRoomScheduleQueryRepository classRoomScheduleQueryRepository;

    @Override
    public PageView<ClassRoomScheduleSummaryView> getClassRoomScheduleSummaryView(Pageable pageable, ClassRoomId classRoomId) {
        return PageView.fromPage(classRoomScheduleQueryRepository.getClassRoomScheduleView(pageable, classRoomId));

    }

    @Override
    public PageView<ClassRoomScheduleSummaryForSchoolView> getClassRoomsSchedulesSummaryForSchoolView(Pageable pageable, SchoolId schoolId) {
        Page<ClassRoomScheduleSummaryForSchoolView> view = classRoomScheduleQueryRepository.getClassRoomScheduleForSchoolView(pageable, schoolId);

        if (view.isEmpty()) {
            return PageView.fromPage(view);
        }

        // map to result page that contains list of schedules
        Page<ClassRoomScheduleSummaryForSchoolView> result = view.map(v -> new ClassRoomScheduleSummaryForSchoolView(
                v.classRoomId(),
                v.classRoomName(),
                classRoomScheduleQueryRepository.getClassRoomScheduleView(v.classRoomId())
        ));

        return PageView.fromPage(result);
    }
}
