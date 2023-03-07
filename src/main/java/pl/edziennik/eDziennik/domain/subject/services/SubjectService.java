package pl.edziennik.eDziennik.domain.subject.services;

import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface SubjectService {

    SubjectResponseApiDto createNewSubject(final SubjectRequestApiDto dto);

    SubjectResponseApiDto findSubjectById(final Long id);

    void deleteSubjectById(final Long id);

    Page<List<SubjectResponseApiDto>> findAllSubjects(PageRequest pageRequest);

    SubjectResponseApiDto updateSubject(final Long id, final SubjectRequestApiDto requestApiDto);
}
