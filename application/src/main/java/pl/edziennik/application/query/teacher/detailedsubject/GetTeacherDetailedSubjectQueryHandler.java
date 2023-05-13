package pl.edziennik.application.query.teacher.detailedsubject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.teacher.TeacherDetailedSubjectDto;

@Component
@AllArgsConstructor
class GetTeacherDetailedSubjectQueryHandler implements IQueryHandler<GetTeacherDetailedSubjectQuery, TeacherDetailedSubjectDto> {


    @Override
    public TeacherDetailedSubjectDto handle(GetTeacherDetailedSubjectQuery command) {
        return null;
    }
}
