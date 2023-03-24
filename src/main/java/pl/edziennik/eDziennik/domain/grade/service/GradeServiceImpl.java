package pl.edziennik.eDziennik.domain.grade.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.mapper.GradeMapper;
import pl.edziennik.eDziennik.domain.grade.repository.GradeRepository;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GradeServiceImpl extends BaseService implements GradeService {

    private final GradeRepository repository;

    @Override
    @Transactional
    public GradeResponseApiDto addNewGrade(GradeRequestApiDto dto) {
        Grade grade = GradeMapper.toEntity(dto);
        Grade savedGrade = repository.save(grade);
        return GradeMapper.toDto(savedGrade);
    }

    @Override
    public GradeResponseApiDto findGradeById(Long id) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("not.found.message", id, Grade.class.getSimpleName())));
        return GradeMapper.toDto(grade);
    }

    @Override
    public void deleteGradeById(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    @Transactional
    public GradeResponseApiDto updateGrade(Long id, GradeRequestApiDto dto) {
        // TODO -> Walidacja
        Optional<Grade> optionalGrade = repository.findById(id);

        if (optionalGrade.isPresent()) {
            Grade grade = optionalGrade.get();
            grade.setGrade(Grade.GradeConst.getByRating(dto.getGrade()));
            grade.setWeight(dto.getWeight());
            grade.setDescription(dto.getDescription());
            return GradeMapper.toDto(grade);
        }

        Grade grade = repository.save(GradeMapper.toEntity(dto));
        return GradeMapper.toDto(grade);

    }

    @Override
    public List<GradeResponseApiDto> findAllGrades() {
        return repository.findAll()
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }
}
