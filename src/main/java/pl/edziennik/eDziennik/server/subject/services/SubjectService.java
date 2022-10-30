package pl.edziennik.eDziennik.server.subject.services;

import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

import java.util.List;

public interface SubjectService {

    SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto);

    SubjectResponseApiDto findSubjectById(final Long id);

    void deleteSubjectById(final Long id);

    List<SubjectResponseApiDto> findAllSubjects();

}
