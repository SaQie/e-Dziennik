package pl.edziennik.application.query.classroom;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomQueryRepository;

@Component
@AllArgsConstructor
class GetClassRoomForSchoolQueryHandler implements IQueryHandler<GetClassRoomForSchoolQuery, PageView<ClassRoomForSchoolView>> {

    private final ClassRoomQueryRepository classRoomQueryRepository;

    @Override
    public PageView<ClassRoomForSchoolView> handle(GetClassRoomForSchoolQuery command) {
        Page<ClassRoomForSchoolView> view = classRoomQueryRepository.getClassRoomForSchoolView(command.pageable(), command.schoolId());

        return PageView.fromPage(view);
    }
}
