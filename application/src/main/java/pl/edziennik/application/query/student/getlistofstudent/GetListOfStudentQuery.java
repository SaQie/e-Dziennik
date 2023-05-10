package pl.edziennik.application.query.student.getlistofstudent;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentDto;

@HandledBy(handler = GetListOfStudentQueryHandler.class)
public record GetListOfStudentQuery(

        Pageable pageable

) implements IQuery<PageDto<StudentDto>> {
}
