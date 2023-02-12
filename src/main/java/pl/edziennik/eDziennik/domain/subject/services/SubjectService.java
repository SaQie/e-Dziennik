package pl.edziennik.eDziennik.domain.subject.services;

import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;

import java.util.List;

public interface SubjectService {

    SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto);

    SubjectResponseApiDto findSubjectById(final Long id);

    void deleteSubjectById(final Long id);

    List<SubjectResponseApiDto> findAllSubjects();

    SubjectResponseApiDto updateSubject(final Long id, final SubjectRequestApiDto requestApiDto);
}
