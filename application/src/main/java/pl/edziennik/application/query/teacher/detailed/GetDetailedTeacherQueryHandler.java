package pl.edziennik.application.query.teacher.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.infrastructure.repository.teacher.TeacherQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedTeacherQueryHandler implements IQueryHandler<GetDetailedTeacherQuery, DetailedTeacherView> {

    private final TeacherQueryRepository teacherQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedTeacherView handle(GetDetailedTeacherQuery query) {
        DetailedTeacherView view = teacherQueryRepository.getTeacherView(query.teacherId());

        if (view == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedTeacherQuery.TEACHER_ID, query.teacherId())
            );
        }

        return view;
    }
}
