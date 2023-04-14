package pl.edziennik.eDziennik.domain.grade.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.mapper.GradeMapper;
import pl.edziennik.eDziennik.domain.grade.repository.GradeRepository;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import java.util.Optional;

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
    public GradeResponseApiDto findGradeById(final GradeId gradeId) {
        Grade grade = repository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("not.found.message", gradeId, Grade.class.getSimpleName())));
        return GradeMapper.toDto(grade);
    }

    @Override
    public void deleteGradeById(final GradeId gradeId) {
        repository.findById(gradeId).ifPresent(repository::delete);
    }

    @Override
    @Transactional
    public GradeResponseApiDto updateGrade(final GradeId gradeId, GradeRequestApiDto dto) {
        Optional<Grade> optionalGrade = repository.findById(gradeId);

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
}
