package pl.edziennik.eDziennik.domain.parent.service;

import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;

import java.util.List;

public interface ParentService {

    ParentResponseApiDto register(ParentRequestApiDto dto);

    ParentResponseApiDto findById(Long id);

    void deleteById(Long id);

    List<ParentResponseApiDto> findAll();
}
