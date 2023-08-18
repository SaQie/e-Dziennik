package pl.edziennik.application.query.schoolclass.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.StudentSummaryForSchoolClassView;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetDetailedSchoolClassQueryHandler implements IQueryHandler<GetDetailedSchoolClassQuery, DetailedSchoolClassView> {

    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSchoolClassView handle(GetDetailedSchoolClassQuery query) {
        DetailedSchoolClassView viewHeader = schoolClassQueryRepository.getSchoolClassView(query.schoolClassId());
        List<StudentSummaryForSchoolClassView> studentsSummary = schoolClassQueryRepository.getSchoolClassStudentsSummaryView(query.schoolClassId());

        if (viewHeader == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedSchoolClassQuery.SCHOOL_CLASS_ID, query.schoolClassId())
            );
        }
        return new DetailedSchoolClassView(viewHeader, studentsSummary);
    }
}
