package pl.edziennik.application.query.teacher.detailedteacher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.infrastructure.repositories.teacher.TeacherQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetDetailedTeacherQueryHandler implements IQueryHandler<GetDetailedTeacherQuery, DetailedTeacherDto> {

    private final TeacherQueryRepository teacherQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedTeacherDto handle(GetDetailedTeacherQuery query) {
        DetailedTeacherDto dto = teacherQueryRepository.getTeacher(query.teacherId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedTeacherQuery.TEACHER_ID, query.teacherId())
            );
        }

        return dto;
    }
}
