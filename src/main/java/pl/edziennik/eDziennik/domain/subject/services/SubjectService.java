package pl.edziennik.eDziennik.domain.subject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;

public interface SubjectService {

    SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto);

    SubjectResponseApiDto findSubjectById(final Long id);

    void deleteSubjectById(final Long id);

    Page<SubjectResponseApiDto> findAllSubjects(Pageable pageable);

    SubjectResponseApiDto updateSubject(final Long id, final SubjectRequestApiDto requestApiDto);
}
