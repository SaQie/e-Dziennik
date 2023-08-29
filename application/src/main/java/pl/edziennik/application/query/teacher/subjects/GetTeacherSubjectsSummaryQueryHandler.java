package pl.edziennik.application.query.teacher.subjects;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.infrastructure.repository.teacher.TeacherQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherSubjectsSummaryQueryHandler implements IQueryHandler<GetTeacherSubjectsSummaryQuery, PageView<TeacherSubjectsSummaryView>> {

    private final TeacherQueryRepository teacherQueryRepository;

    @Override
    public PageView<TeacherSubjectsSummaryView> handle(GetTeacherSubjectsSummaryQuery command) {
        Page<TeacherSubjectsSummaryView> views = teacherQueryRepository.getTeacherSubjectsSummaryWithPagination(command.pageable(), command.teacherId());

        return PageView.fromPage(views);
    }
}
