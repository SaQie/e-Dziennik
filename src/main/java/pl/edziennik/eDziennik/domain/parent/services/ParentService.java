package pl.edziennik.eDziennik.domain.parent.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;

public interface ParentService {

    ParentResponseApiDto register(ParentRequestApiDto dto);

    ParentResponseApiDto findById(Long id);

    void deleteById(Long id);

    Page<ParentResponseApiDto> findAll(Pageable pageable);
}
