package pl.edziennik.application.query.classroom;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomQueryRepository;

@Repository
@AllArgsConstructor
class ClassRoomQueryDaoImpl implements ClassRoomQueryDao {

    private final ClassRoomQueryRepository classRoomQueryRepository;


    @Override
    public PageView<ClassRoomForSchoolView> getClassRoomSummaryForSchoolView(Pageable pageable, SchoolId schoolId) {
        return PageView.fromPage(classRoomQueryRepository.getClassRoomForSchoolView(pageable, schoolId));
    }
}
