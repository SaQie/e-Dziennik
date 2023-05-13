package pl.edziennik.application.query.subject.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
import pl.edziennik.infrastructure.repositories.subject.SubjectQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetDetailedSubjectQueryHandler implements IQueryHandler<GetDetailedSubjectQuery, DetailedSubjectDto> {

    private final SubjectQueryRepository subjectQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSubjectDto handle(GetDetailedSubjectQuery query) {
        DetailedSubjectDto dto = subjectQueryRepository.getSubject(query.subjectId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedSubjectQuery.SUBJECT_ID, query.subjectId())
            );
        }

        return dto;
    }
}
