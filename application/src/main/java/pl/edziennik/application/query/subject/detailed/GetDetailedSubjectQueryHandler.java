package pl.edziennik.application.query.subject.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.infrastructure.repository.subject.SubjectQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedSubjectQueryHandler implements IQueryHandler<GetDetailedSubjectQuery, DetailedSubjectView> {

    private final SubjectQueryRepository subjectQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSubjectView handle(GetDetailedSubjectQuery query) {
        DetailedSubjectView view = subjectQueryRepository.getSubjectView(query.subjectId());

        if (view == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedSubjectQuery.SUBJECT_ID, query.subjectId())
            );
        }

        return view;
    }
}
