package pl.edziennik.eDziennik.domain.subject.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

public interface SubjectService {

    SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto);

    SubjectResponseApiDto findSubjectById(final SubjectId subjectId);

    void deleteSubjectById(final SubjectId subjectId);

    PageDto<SubjectResponseApiDto> findAllSubjects(final Pageable pageable);

    SubjectResponseApiDto updateSubject(final SubjectId subjectId, final SubjectRequestApiDto requestApiDto);
}
